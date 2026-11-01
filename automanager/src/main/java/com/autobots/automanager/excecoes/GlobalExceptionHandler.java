package com.autobots.automanager.excecoes;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DocumentoDuplicadoException.class)
    public ResponseEntity<Map<String, String>> tratarDocumentoDuplicado(
            DocumentoDuplicadoException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(resposta);
        }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarValidacao(
            MethodArgumentNotValidException exception) {

        Map<String, String> resposta = new HashMap<>();

        exception.getBindingResult()
            .getFieldErrors()
            .forEach(erro -> resposta.put(
                    erro.getField(),
                    erro.getDefaultMessage()
            ));

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(resposta);
    }

    @ExceptionHandler(DocumentoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> tratarDocumentoNaoEncontrado(
            DocumentoNaoEncontradoException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(resposta);
    }

    @ExceptionHandler(EnderecoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> tratarEnderecoNaoEncontrado(
            EnderecoNaoEncontradoException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(resposta);
    }

    @ExceptionHandler(TelefoneNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> tratarTelefoneNaoEncontrado(
            TelefoneNaoEncontradoException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(resposta);
    }

        @ExceptionHandler(ServicoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> tratarServicoNaoEncontrado(
            ServicoNaoEncontradoException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(resposta);
    }

    @ExceptionHandler(MercadoriaNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> tratarMercadoriaNaoEncontrada(
            MercadoriaNaoEncontradaException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(resposta);
    }

    @ExceptionHandler(DatasMercadoriaInvalidasException.class)
    public ResponseEntity<Map<String, String>> tratarDatasMercadoriaInvalidas(
            DatasMercadoriaInvalidasException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(resposta);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> tratarCorpoInvalido(
            HttpMessageNotReadableException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", "Corpo da requisição inválido. Verifique datas (yyyy-MM-dd) e valores de enums.");

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(resposta);
    }

    @ExceptionHandler(EmailNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> tratarEmailNaoEncontrado(
            EmailNaoEncontradoException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(resposta);
    }

    @ExceptionHandler(CredencialNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> tratarCredencialNaoEncontrada(
            CredencialNaoEncontradaException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(resposta);
    }

    @ExceptionHandler(CredencialDuplicadaException.class)
    public ResponseEntity<Map<String, String>> tratarCredencialDuplicada(
            CredencialDuplicadaException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(resposta);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> tratarUsuarioNaoEncontrado(
            UsuarioNaoEncontradoException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(resposta);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> tratarParametroInvalido(
            MethodArgumentTypeMismatchException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", "Valor inválido para o parâmetro: " + exception.getName());

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(resposta);
    }

    @ExceptionHandler(AssociacaoJaExistenteException.class)
    public ResponseEntity<Map<String, String>> tratarAssociacaoJaExistente(
            AssociacaoJaExistenteException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(resposta);
    }

    @ExceptionHandler(AssociacaoNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> tratarAssociacaoNaoEncontrada(
            AssociacaoNaoEncontradaException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(resposta);
    }

    @ExceptionHandler(VeiculoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> tratarVeiculoNaoEncontrado(
            VeiculoNaoEncontradoException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(resposta);
    }

    @ExceptionHandler(PlacaDuplicadaException.class)
    public ResponseEntity<Map<String, String>> tratarPlacaDuplicada(
            PlacaDuplicadaException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(resposta);
    }

    @ExceptionHandler(ProprietarioInvalidoException.class)
    public ResponseEntity<Map<String, String>> tratarProprietarioInvalido(
            ProprietarioInvalidoException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(resposta);
    }

    @ExceptionHandler(ExclusaoNaoPermitidaException.class)
    public ResponseEntity<Map<String, String>> tratarExclusaoNaoPermitida(
            ExclusaoNaoPermitidaException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(resposta);
    }

    @ExceptionHandler(EmpresaNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> tratarEmpresaNaoEncontrada(
            EmpresaNaoEncontradaException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(resposta);
    }

    @ExceptionHandler(RazaoSocialDuplicadaException.class)
    public ResponseEntity<Map<String, String>> tratarRazaoSocialDuplicada(
            RazaoSocialDuplicadaException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(resposta);
    }

    @ExceptionHandler(VendaNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> tratarVendaNaoEncontrada(
            VendaNaoEncontradaException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(resposta);
    }

    @ExceptionHandler(IdentificacaoDuplicadaException.class)
    public ResponseEntity<Map<String, String>> tratarIdentificacaoDuplicada(
            IdentificacaoDuplicadaException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(resposta);
    }

    @ExceptionHandler(VendaInvalidaException.class)
    public ResponseEntity<Map<String, String>> tratarVendaInvalida(
            VendaInvalidaException exception) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", exception.getMessage());

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(resposta);
    }
}