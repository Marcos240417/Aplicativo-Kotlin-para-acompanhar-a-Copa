package com.example.mycopa.data.remote

// Esta é a classe que estava faltando no seu código
class UnexpectedException : Exception("Ocorreu um erro inesperado")

// Exceção personalizada para substituir a do framework Android
class NotFoundException(message: String) : Exception(message)