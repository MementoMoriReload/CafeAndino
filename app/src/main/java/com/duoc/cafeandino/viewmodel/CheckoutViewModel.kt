// CheckoutViewModel.kt
package com.duoc.cafeandino.viewmodel

import androidx.lifecycle.ViewModel
import com.duoc.cafeandino.model.PaymentMethod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CheckoutViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    fun onNameChange(value: String) {
        _uiState.value = _uiState.value.copy(name = value)
    }

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(email = value.trim())
    }

    fun onPhoneChange(value: String) {
        // Restringimos la entrada: solo dígitos y máximo 9.
        // Esto no es validar, es evitar que pueda escribir algo imposible.
        val digits = value.filter { it.isDigit() }.take(9)
        _uiState.value = _uiState.value.copy(phone = digits)
    }

    fun onAddressChange(value: String) {
        _uiState.value = _uiState.value.copy(address = value)
    }

    fun onTipChange(value: Int) {
        _uiState.value = _uiState.value.copy(tipPercent = value)
    }

    fun onPaymentMethodChange(value: PaymentMethod) {
        _uiState.value = _uiState.value.copy(paymentMethod = value)
    }

    fun onInvoiceChange(value: Boolean) {
        _uiState.value = _uiState.value.copy(wantsInvoice = value)
    }

    fun onTermsChange(value: Boolean) {
        _uiState.value = _uiState.value.copy(acceptsTerms = value)
    }

    /**
     * Intenta confirmar el pedido.
     * Devuelve true si el formulario era válido, false si hay errores por corregir.
     * En los dos casos activa la visualización de errores.
     */
    fun submit(): Boolean {
        _uiState.value = _uiState.value.copy(showErrors = true)
        return _uiState.value.isValid
    }

    /** Deja el formulario limpio para el próximo pedido. */
    fun reset() {
        _uiState.value = CheckoutUiState()
    }
}