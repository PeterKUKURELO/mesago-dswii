package com.mesago.mspedidos.exception;

public class PedidoNoEncontradoException extends RuntimeException {
    public PedidoNoEncontradoException(Long id) {
        super("El pedido con ID " + id + " no existe");
    }
}
