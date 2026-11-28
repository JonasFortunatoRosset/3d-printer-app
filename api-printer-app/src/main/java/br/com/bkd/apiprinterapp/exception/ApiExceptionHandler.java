package br.com.bkd.apiprinterapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validacao(MethodArgumentNotValidException excecao) {
        Map<String, String> erros = new LinkedHashMap<>();
        excecao.getBindingResult().getFieldErrors().forEach(erro ->
                erros.putIfAbsent(erro.getField(), erro.getDefaultMessage()));

        ProblemDetail problema = criarProblema(HttpStatus.BAD_REQUEST, "Erro de validação",
                "Um ou mais campos são inválidos");
        problema.setProperty("errors", erros);
        return problema;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail corpoInvalido(HttpMessageNotReadableException excecao) {
        return criarProblema(HttpStatus.BAD_REQUEST, "Requisição inválida",
                "O corpo da requisição está malformado ou contém valores inválidos");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail parametroInvalido(MethodArgumentTypeMismatchException excecao) {
        return criarProblema(HttpStatus.BAD_REQUEST, "Parâmetro inválido",
                "Valor inválido para o parâmetro '" + excecao.getName() + "'");
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ProblemDetail consultaInvalida(InvalidDataAccessApiUsageException excecao) {
        return criarProblema(HttpStatus.BAD_REQUEST, "Consulta inválida",
                "Parâmetros de consulta inválidos. Verifique o campo informado em 'sort'");
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ProblemDetail naoEncontrado(RecursoNaoEncontradoException excecao) {
        return criarProblema(HttpStatus.NOT_FOUND, "Recurso não encontrado", excecao.getMessage());
    }

    @ExceptionHandler(ConflitoException.class)
    public ProblemDetail conflito(ConflitoException excecao) {
        return criarProblema(HttpStatus.CONFLICT, "Conflito", excecao.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail violacaoIntegridade(DataIntegrityViolationException excecao) {
        return criarProblema(HttpStatus.CONFLICT, "Conflito de integridade",
                "A operação viola uma restrição do banco de dados");
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail generico(Exception excecao) {
        if (excecao instanceof ErrorResponse erroSpring) {
            return erroSpring.getBody();
        }
        log.error("Erro inesperado", excecao);
        return criarProblema(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno", "Ocorreu um erro inesperado");
    }

    private ProblemDetail criarProblema(HttpStatus status, String titulo, String detalhe) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(status, detalhe);
        problema.setTitle(titulo);
        return problema;
    }
}
