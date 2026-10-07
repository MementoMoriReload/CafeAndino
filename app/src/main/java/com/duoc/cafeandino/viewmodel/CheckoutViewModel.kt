// CheckoutViewModel.kt
package com.duoc.cafeandino.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.cafeandino.model.PaymentMethod
import com.duoc.cafeandino.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckoutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserPreferencesRepository(application)

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    // Propina por defecto vigente: con ella parte cada formulario nuevo
    private var defaultTipPercent = CheckoutUiState().tipPercent

    init {
        // Carga lo guardado y lo aplica al formulario
        viewModelScope.launch {
            repository.userPreferences.collect { saved ->
                defaultTipPercent = saved.defaultTipPercent
                _uiState.value = _uiState.value.copy(
                    name = saved.customerName,
                    email = saved.customerEmail,
                    tipPercent = saved.defaultTipPercent
                )
            }
        }
    }

    fun onNameChange(value: String) {
        _uiState.value = _uiState.value.copy(name = value)
    }

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(email = value.trim())
    }

    fun onPhoneChange(value: String) {
        // Restringimos la entrada: solo dígitos y máximo 9.
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
     * Si era válido, además guarda el nombre y el correo para el próximo pedido.
     */
    fun submit(): Boolean {
        _uiState.value = _uiState.value.copy(showErrors = true)
        val state = _uiState.value
        if (state.isValid) {
            viewModelScope.launch {
                repository.saveCustomerData(state.name.trim(), state.email.trim())
            }
        }
        return state.isValid
    }

    /** Deja el formulario listo para el próximo pedido: conserva nombre y correo. */
    fun reset() {
        val current = _uiState.value
        _uiState.value = CheckoutUiState(
            name = current.name.trim(),
            email = current.email,
            tipPercent = defaultTipPercent
        )
    }
}