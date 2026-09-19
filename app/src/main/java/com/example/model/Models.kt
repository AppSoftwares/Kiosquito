package com.example.model

import java.math.BigDecimal
import java.math.RoundingMode

enum class PricingMode {
    DETALLE,
    MAYOREO
}

enum class UnitType {
    UNIT,
    WEIGHT_KG
}

data class CartItem(
    val id: String,
    val name: String,
    val subtitle: String,
    val imageUrl: String,
    val unitType: UnitType,
    val costPriceUsd: BigDecimal,
    val retailPriceUsd: BigDecimal,
    val wholesalePriceUsd: BigDecimal,
    val quantityUnits: Int = 1,
    val weightGrams: Int = 250 // for weight items (e.g. 100g, 250g, 500g, 1000g)
) {
    fun getEffectivePrice(mode: PricingMode): BigDecimal {
        val basePrice = if (mode == PricingMode.DETALLE) retailPriceUsd else wholesalePriceUsd
        return when (unitType) {
            UnitType.UNIT -> basePrice.multiply(BigDecimal(quantityUnits))
            UnitType.WEIGHT_KG -> basePrice.multiply(BigDecimal(weightGrams)).divide(BigDecimal(1000), 2, RoundingMode.HALF_UP)
        }
    }
}

data class InventoryProduct(
    val id: String,
    val name: String,
    val category: String,
    val subtitle: String,
    val imageUrl: String,
    val unitType: UnitType,
    val costPriceUsd: BigDecimal,
    val retailPriceUsd: BigDecimal,
    val wholesalePriceUsd: BigDecimal,
    val stockQty: Double, // Stock quantity can remain double if it represents physical units/kg
    val minStockQty: Double,
    val isPesable: Boolean = false,
    val supplier: String = "Distribuidora Los Andes",
    val isCritical: Boolean = false
)

data class SupplierInvoice(
    val id: String,
    val invoiceNumber: String,
    val supplierName: String,
    val categoryDescription: String,
    val dueDateText: String,
    val pendingBalanceUsd: BigDecimal,
    val isCredit: Boolean = true
)

data class TrustedCustomer(
    val id: String,
    val name: String,
    val aliasOrAddress: String,
    val avatarUrl: String,
    val debtUsd: BigDecimal,
    val creditLimitUsd: BigDecimal,
    val lastPaymentText: String,
    val pendingPurchasesCount: Int,
    val isExceeded: Boolean = false,
    val isUpToDate: Boolean = true,
    val isGoodPayer: Boolean = false
)

enum class PaymentMethodType {
    DIVISAS_USD,
    PAGO_MOVIL,
    EFECTIVO_BS,
    PUNTO_VENTA,
    FIAO_LIBRETA
}
