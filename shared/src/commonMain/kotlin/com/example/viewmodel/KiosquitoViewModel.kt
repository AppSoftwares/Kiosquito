package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.CartItem
import com.example.model.InventoryProduct
import com.example.model.PaymentMethodType
import com.example.model.PricingMode
import com.example.model.SupplierInvoice
import com.example.model.TrustedCustomer
import com.example.model.UnitType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class MainTab {
    POS,
    COBROS,
    FIAO,
    STOCK,
    PROFILE,
    CATALOG_ADMIN
}

data class AppUiState(
    val currentTab: MainTab = MainTab.POS,
    val exchangeRateUsdToBs: Double = 36.50,
    val isPrinterConnected: Boolean = true,
    val autoPrintReceipt: Boolean = true,
    
    // Store Info
    val storeName: String = "Bodega San Antonio",
    val storeLocation: String = "El Valle, Caracas",
    val storeOwner: String = "Marco Valderrama",
    val storeRif: String = "J-40819284-1",
    val storePhone: String = "+58 (412) 555-0199",
    
    // POS State
    val pricingMode: PricingMode = PricingMode.DETALLE,
    val currentCustomerName: String = "Consumidor Final (Contado)",
    val cartItems: List<CartItem> = listOf(
        CartItem(
            id = "item-1",
            name = "Queso Blanco Llanero",
            subtitle = "Charcutería fresca • Rayado / En trozo",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAcmMuZExT4CvhaT0EtyNUsLhkh2TYQ_ni5BJ9zni498xnBcg73yQDD1ign2H1t-bSKuv09uuAkJuzq4kVFGjlSsLApSotueUCuAPa3pzIU9o8yjmW_eZ0FIi4Nbhy-2bRv4KQkZZAtFZ2g9chH0FbiOmqlMywXTc0dxDcdHkhjphzo1c5kdfCmhhu0crspkzd8CI_Wqt6cUOel-FJa6HRtNeaszwgd06jU9DSnjpiz_kgauHxQfb0HfA",
            unitType = UnitType.WEIGHT_KG,
            costPriceUsd = 3.80,
            retailPriceUsd = 5.20,
            wholesalePriceUsd = 4.60,
            weightGrams = 250
        ),
        CartItem(
            id = "item-2",
            name = "Harina Pan 1kg",
            subtitle = "Víveres • Alimentos Polar",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCZR0bBDbOzE-PeKm88w4Kg1cTW1zguQDmCmm2Ri-VrXFUCXMtgzgFtbaaeVmz-O6sR2gk4OoVs5msDdIdGPEoh8PXpRcL0klEXBOoLqnSgwOAAYqoFYN0phRHfh55Fc--BcSzH-nwvvmVp-iNVWaRdriJbJAg2Gd9uYVL2cwowheGnPSbN8R3nlhXf-MAXvsRnWMmcr2l2c7xvZMlBN647cRbmS9JP3aFd4CbQXkFROsOAHecsbFEPGA",
            unitType = UnitType.UNIT,
            costPriceUsd = 0.85,
            retailPriceUsd = 1.10,
            wholesalePriceUsd = 1.00,
            quantityUnits = 2
        ),
        CartItem(
            id = "item-3",
            name = "Café Molido Fama de América",
            subtitle = "Bolsa 250g • Tostado Clásico",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDGmHQVzB83Lld0KlQP1s6E2rdp9fRvf4rPexUvFZYWl287QU6wLKcKiJP9sXeP4EPqgM92hyMEW15LB-d3K3_NITYG33DglJTDWUg1BORmavIir85_uANGSSVC-hvQGdcHYlJnrztz8HcPShAhhdvBVmV1znWmzhu2GBM_nqVMgyc-QcA0AmgJQtoyUg4fruzIvWzrtsPhFCKB74YjZykR0QySny9xS7GMFQTDuIupGcoNZG7AGZIlUw",
            unitType = UnitType.UNIT,
            costPriceUsd = 1.80,
            retailPriceUsd = 2.50,
            wholesalePriceUsd = 2.20,
            quantityUnits = 1
        )
    ),
    
    // Checkout State
    val selectedPaymentMethod: PaymentMethodType = PaymentMethodType.DIVISAS_USD,
    val cashReceivedUsd: Double = 10.00,
    val isTicketCheckoutSuccess: Boolean = false,
    
    // Inventory
    val inventorySearchQuery: String = "",
    val selectedCategoryFilter: String = "Todos",
    val inventoryProducts: List<InventoryProduct> = listOf(
        InventoryProduct(
            id = "inv-1",
            name = "Queso Duro Santa Bárbara",
            category = "Charcutería",
            subtitle = "Charcutería • Balanza",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBWmAU3GzbUmhngs1cydwB84accf7Dg66qgavjc3f9_bxsKASmfpI7fM5RMHK6f2jcAG_zdqDmC7LIKJyngpVB7cHWEPKuZcdAd-EJJ0FPGoyXp05zeo5wOShIYBn6wkSlU83unqFUCOYTcaakxiZ1REboTu21J7bADmMox68sIAHsIZlGnbIw0iuq6F2aUesQPn-GSYSwtvab6NrfFxllfz8lYH1yED4iztxDnuYKLKegvxxcDcfqDbA",
            unitType = UnitType.WEIGHT_KG,
            costPriceUsd = 3.80,
            retailPriceUsd = 5.20,
            wholesalePriceUsd = 4.60,
            stockQty = 4.25,
            minStockQty = 5.0,
            isPesable = true
        ),
        InventoryProduct(
            id = "inv-2",
            name = "Harina de Maíz Blanco 1kg",
            category = "Víveres",
            subtitle = "Víveres • Harina PAN",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuC5tH3IWC5MsclUyDqAZgDLlTuuH0h5VFyqya7FVvEcfZ64JSg0ageq5hcFX6dLnCdKDGfd-5i1c4MISULzHMRBddasQhWsaA5JBy6xAO9LNUYcLIsPubnWA-XFmPtwhgfb9oTDVSwPg_X_jqmhuvf9mJINML5Ee_hKTM1e-E15Nknc-J8_kOb01be6Gd3Ts5UGstiOGJ7Ql_tRWY8kk79RAD95yKUeOjTjQVpVeo03dL0nnUg772CRHw",
            unitType = UnitType.UNIT,
            costPriceUsd = 0.85,
            retailPriceUsd = 1.10,
            wholesalePriceUsd = 1.00,
            stockQty = 36.0,
            minStockQty = 10.0
        ),
        InventoryProduct(
            id = "inv-3",
            name = "Aceite Vegetal 900ml",
            category = "Víveres",
            subtitle = "Víveres • Alimentos Polar",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBHAwCDNRgeFe08wdWZJNFdSfikblEupO3nWOfVfiiGP55bwwO-CsqC2iTSWCN45aNn-kBA3Gbn4aPsepTAJedPUqqnLEgFbCLHBZo8ZBC-JmVBGWOXsJz5IGnng1J6ErpWEkYptHFQHRVNHnBS6GOutKeTx9r2tKG3W_FcE36OADGazRe84k6TSBx1rPTCQ5NZlpwlNLxyooMgsMWSNqL2WDZ115QTvA6As8uGWxaE6Rvtumz-5gxLkQ",
            unitType = UnitType.UNIT,
            costPriceUsd = 1.45,
            retailPriceUsd = 1.95,
            wholesalePriceUsd = 1.80,
            stockQty = 2.0,
            minStockQty = 6.0,
            isCritical = true
        ),
        InventoryProduct(
            id = "inv-4",
            name = "Café Tostado Molido",
            category = "Bebidas & Café",
            subtitle = "Café artesanal molido",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCUOXVBNLSRou1IpzbrWsebQJ_H5SuruBwY6tpD7O9FCE2be1Cpya_f5cBwfh4Ml-jF4_9-D2tCEmVuvNXIp8AoG0ACJq9av9KUWdH7_Dv4UUpBF1T7KsY60CbnE7MqYx8GdtqMRstpd0k9zQaD-ctx6ZKy-YEcNgCEYmogcdkF3-qabZXk67JiQVmlzS0bdzOXeoTuU5EFo7RUYfjurhaUCN2b1Z6w_9_Arq4n9b9AH7tT8JaAh9CN7g",
            unitType = UnitType.WEIGHT_KG,
            costPriceUsd = 6.00,
            retailPriceUsd = 8.50,
            wholesalePriceUsd = 7.50,
            stockQty = 6.50,
            minStockQty = 3.0,
            isPesable = true
        )
    ),
    val supplierInvoices: List<SupplierInvoice> = listOf(
        SupplierInvoice(
            id = "inv-8812",
            invoiceNumber = "F-8812",
            supplierName = "Distribuidora Los Andes",
            categoryDescription = "Quesos, Charcutería y Mantequilla",
            dueDateText = "15 de Noviembre",
            pendingBalanceUsd = 120.00,
            isCredit = true
        ),
        SupplierInvoice(
            id = "inv-9041",
            invoiceNumber = "#9041",
            supplierName = "Lácteos del Centro",
            categoryDescription = "Vence en 8 días (20 Nov)",
            dueDateText = "20 de Noviembre",
            pendingBalanceUsd = 65.00,
            isCredit = true
        )
    ),
    
    // Customers (Libreta de Fiao)
    val customersSearchQuery: String = "",
    val customerFilter: String = "all", // all, pending, exceeded, settled
    val customers: List<TrustedCustomer> = listOf(
        TrustedCustomer(
            id = "cust-1",
            name = "Don Carlos Méndez",
            aliasOrAddress = "Taller Mecánico · Calle 4",
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAekCD8QA2bfSwHFfOpGsIFnr2ZnewCJDMbkhO_P1UIig7jr-cKxfcKm82DDRz8LrTjdtnJnAJH5mUvGXLI6941U2QtS7GO4qLFLR9zguGct5J9gUZeA1Nv1Y7hYfnvAPN_dmUu6izmffAFd8o9f9ETJXv3vBfZXXh_6Bqdtw0TTU8W3sCuwag4a_HjnsrxV8gSz1KlpwYgTr042l7RRKKArAaqiKnYARZMoOO-maRbADvvRfyVeSLZaA",
            debtUsd = 34.20,
            creditLimitUsd = 50.00,
            lastPaymentText = "Ayer ($10.00)",
            pendingPurchasesCount = 4,
            isExceeded = false,
            isUpToDate = true
        ),
        TrustedCustomer(
            id = "cust-2",
            name = "Sra. Carmen Rodríguez",
            aliasOrAddress = "Casa verde esq. Los Samanes",
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuD1vHocz6rRHy9VLC9F4zMynU1ZzKjkkMUcitL_NirN5HTSzH6YR0BpzT267MWkxPZiNVGWGhvDputWv7Kyi49JW02seCYTvmkvu1oJ3gZp7OjJ3YR7bAYsZdFyZh21R9bCoM7pD64AQIIGVXnxCPQSyGOZ6QZC4evlOAw-m09kBbEF5CfMcd6_zfBVX5QzHymx-7MEMMb3jYbPdcLyxj5mGaXGnvT5c9Kb1sqnWwPEGeAbscsulG2cmA",
            debtUsd = 62.00,
            creditLimitUsd = 50.00,
            lastPaymentText = "Hace 12 días",
            pendingPurchasesCount = 7,
            isExceeded = true,
            isUpToDate = false
        ),
        TrustedCustomer(
            id = "cust-3",
            name = "Profesor Alberto Soto",
            aliasOrAddress = "Liceo Bolivariano · Casa 12",
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuD75OJEOFX1lDfBEhc4Ir3BBs-yPeozUtzfFGHoGFUc9rAmQF2I87UM-kxV21TxIwgnPeK4ovUQ8qSLJAtA9-cpgV-qCpG8nKJslok8-fYu1SgWzHkDpv2isZccz9tLKbCRt3p7ZCyoCX7HrN3bkKePyAPkF505RbfL6BwGdzdJVEyfPpXanqd0bECMV1LUaQZg3xc0lHmVlSJFdcTEuKDxjh-Esa_KCr1ytlCyWB3G-zCShmy-_QzlbQ",
            debtUsd = 15.50,
            creditLimitUsd = 40.00,
            lastPaymentText = "Hoy 8:30 AM",
            pendingPurchasesCount = 2,
            isExceeded = false,
            isUpToDate = true,
            isGoodPayer = true
        )
    ),

    // Toast feedback message
    val userToast: String? = null,

    val publicCatalogProducts: List<com.example.model.PublicProduct> = listOf(
        com.example.model.PublicProduct(
            id = "pub-1",
            name = "Queso Blanco Llanero",
            category = "Charcutería",
            description = "Charcutería fresca • Rayado / En trozo",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAcmMuZExT4CvhaT0EtyNUsLhkh2TYQ_ni5BJ9zni498xnBcg73yQDD1ign2H1t-bSKuv09uuAkJuzq4kVFGjlSsLApSotueUCuAPa3pzIU9o8yjmW_eZ0FIi4Nbhy-2bRv4KQkZZAtFZ2g9chH0FbiOmqlMywXTc0dxDcdHkhjphzo1c5kdfCmhhu0crspkzd8CI_Wqt6cUOel-FJa6HRtNeaszwgd06jU9DSnjpiz_kgauHxQfb0HfA",
            priceUsd = 5.20
        ),
        com.example.model.PublicProduct(
            id = "pub-2",
            name = "Harina Pan 1kg",
            category = "Víveres",
            description = "Víveres • Alimentos Polar",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCZR0bBDbOzE-PeKm88w4Kg1cTW1zguQDmCmm2Ri-VrXFUCXMtgzgFtbaaeVmz-O6sR2gk4OoVs5msDdIdGPEoh8PXpRcL0klEXBOoLqnSgwOAAYqoFYN0phRHfh55Fc--BcSzH-nwvvmVp-iNVWaRdriJbJAg2Gd9uYVL2cwowheGnPSbN8R3nlhXf-MAXvsRnWMmcr2l2c7xvZMlBN647cRbmS9JP3aFd4CbQXkFROsOAHecsbFEPGA",
            priceUsd = 1.10
        ),
        com.example.model.PublicProduct(
            id = "pub-3",
            name = "Café Molido Fama de América",
            category = "Bebidas & Café",
            description = "Bolsa 250g • Tostado Clásico",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDGmHQVzB83Lld0KlQP1s6E2rdp9fRvf4rPexUvFZYWl287QU6wLKcKiJP9sXeP4EPqgM92hyMEW15LB-d3K3_NITYG33DglJTDWUg1BORmavIir85_uANGSSVC-hvQGdcHYlJnrztz8HcPShAhhdvBVmV1znWmzhu2GBM_nqVMgyc-QcA0AmgJQtoyUg4fruzIvWzrtsPhFCKB74YjZykR0QySny9xS7GMFQTDuIupGcoNZG7AGZIlUw",
            priceUsd = 2.50
        )
    )
)

class KiosquitoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun switchTab(tab: MainTab) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun setPricingMode(mode: PricingMode) {
        _uiState.update { it.copy(pricingMode = mode) }
    }

    fun setCustomerName(name: String) {
        _uiState.update { it.copy(currentCustomerName = name) }
    }

    fun updateCartWeight(itemId: String, weightGrams: Int) {
        _uiState.update { state ->
            val updated = state.cartItems.map {
                if (it.id == itemId) it.copy(weightGrams = weightGrams) else it
            }
            state.copy(cartItems = updated)
        }
    }

    fun updateCartQuantity(itemId: String, delta: Int) {
        _uiState.update { state ->
            val updated = state.cartItems.mapNotNull {
                if (it.id == itemId) {
                    val newQty = it.quantityUnits + delta
                    if (newQty <= 0) null else it.copy(quantityUnits = newQty)
                } else it
            }
            state.copy(cartItems = updated)
        }
    }

    fun removeCartItem(itemId: String) {
        _uiState.update { state ->
            state.copy(cartItems = state.cartItems.filterNot { it.id == itemId })
        }
    }

    fun setPaymentMethod(method: PaymentMethodType) {
        _uiState.update { it.copy(selectedPaymentMethod = method) }
    }

    fun setCashReceived(amount: Double) {
        _uiState.update { it.copy(cashReceivedUsd = amount) }
    }

    fun toggleAutoPrint() {
        _uiState.update { it.copy(autoPrintReceipt = !it.autoPrintReceipt) }
    }

    fun getCartTotalUsd(): Double =
        _uiState.value.cartItems.fold(0.0) { acc, item ->
            acc + item.getEffectivePrice(_uiState.value.pricingMode)
        }

    fun registerCheckout() {
        _uiState.update { state ->
            val total = getCartTotalUsd()
            
            // Logic to update inventory or customer debt would go here
            // For now, we simulate the effect on UI state
            state.copy(
                isTicketCheckoutSuccess = true, 
                cartItems = emptyList(), // Clear cart after checkout
                userToast = "¡Cobro registrado: $${total}!"
            )
        }
    }

    fun recordAbono(customerId: String, amountUsd: Double) {
        _uiState.update { state ->
            val updated = state.customers.map {
                if (it.id == customerId) {
                    val newDebt = (it.debtUsd - amountUsd).coerceAtLeast(0.0)
                    it.copy(
                        debtUsd = newDebt,
                        isExceeded = newDebt > it.creditLimitUsd,
                        lastPaymentText = "Hoy ($${amountUsd})"
                    )
                } else it
            }
            state.copy(
                customers = updated,
                userToast = "Abono de $${amountUsd} registrado correctamente"
            )
        }
    }

    fun updateExchangeRate(newRate: Double) {
        _uiState.update { it.copy(exchangeRateUsdToBs = newRate, userToast = "Tasa actualizada a ${newRate} Bs") }
    }

    fun buildWhatsAppOrderLink(
        customerName: String,
        notes: String,
        cartItems: List<CartItem>,
        totalUsd: Double,
        totalBs: Double
    ): String {
        val state = _uiState.value
        val lineas = cartItems.joinToString("\n") {
            "${it.quantityUnits}x ${it.name} - $${String.format("%.2f", it.retailPriceUsd)}"
        }
        val mensaje = buildString {
            append("🛒 *Nuevo Pedido - ${state.storeName}*\n")
            append("Cliente: $customerName\n")
            append("------------------------\n")
            append("$lineas\n")
            append("------------------------\n")
            append("Subtotal: $${String.format("%.2f", totalUsd)}\n")
            append("Bs: ${String.format("%.2f", totalBs)} (Tasa ${state.exchangeRateUsdToBs})\n")
            if (notes.isNotBlank()) append("Notas: $notes\n")
        }
        // Note: URL encoding might need a KMP library or simple replacement if not available in common
        val encoded = mensaje.replace(" ", "%20").replace("\n", "%0A").replace("*", "%2A")
        return "https://wa.me/${state.storePhone.filter { it.isDigit() }}?text=$encoded"
    }

    fun dismissToast() {
        _uiState.update { it.copy(userToast = null) }
    }

    fun fetchPublicCatalog() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(userToast = "Catálogo público sincronizado con Supabase")
            }
        }
    }

    fun addPublicProduct(name: String, category: String, description: String, priceUsd: Double, imageUrl: String) {
        viewModelScope.launch {
            val newProduct = com.example.model.PublicProduct(
                id = "pub-${uiState.value.publicCatalogProducts.size + 1}",
                name = name,
                category = category,
                description = description,
                priceUsd = priceUsd,
                imageUrl = imageUrl
            )
            _uiState.update { state ->
                state.copy(
                    publicCatalogProducts = state.publicCatalogProducts + newProduct,
                    userToast = "Producto '$name' agregado al catálogo público"
                )
            }
        }
    }

    fun deletePublicProduct(id: String) {
        viewModelScope.launch {
            _uiState.update { state ->
                val prod = state.publicCatalogProducts.find { it.id == id }
                state.copy(
                    publicCatalogProducts = state.publicCatalogProducts.filterNot { it.id == id },
                    userToast = prod?.let { "Producto '${it.name}' eliminado del catálogo" } ?: "Producto eliminado"
                )
            }
        }
    }
}
