package com.example.model

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
    val costPriceUsd: Double,
    val retailPriceUsd: Double,
    val wholesalePriceUsd: Double,
    val quantityUnits: Int = 1,
    val weightGrams: Int = 250 // for weight items (e.g. 100g, 250g, 500g, 1000g)
) {
    fun getEffectivePrice(mode: PricingMode): Double {
        val basePrice = if (mode == PricingMode.DETALLE) retailPriceUsd else wholesalePriceUsd
        return when (unitType) {
            UnitType.UNIT -> basePrice * quantityUnits
            UnitType.WEIGHT_KG -> (basePrice * weightGrams) / 1000.0
        }
    }
}

data class InventoryProduct(
    val id: String,
    val name: String,
    val category: String,
    val subtitle: String,
    val description: String = "",       // texto para el cliente final
    val imageUrl: String,
    val unitType: UnitType,
    val costPriceUsd: Double,
    val retailPriceUsd: Double,
    val wholesalePriceUsd: Double,
    val stockQty: Double, // Stock quantity can remain double if it represents physical units/kg
    val minStockQty: Double,
    val isPesable: Boolean = false,
    val supplier: String = "Distribuidora Los Andes",
    val isCritical: Boolean = false,
    val isPublicCatalog: Boolean = true  // visible en el enlace público
)

data class PublicProduct(
    val id: String,
    val name: String,
    val category: String = "General",
    val description: String,
    val imageUrl: String,
    val priceUsd: Double
)

fun InventoryProduct.toPublic() = PublicProduct(
    id = id,
    name = name,
    category = category,
    description = description.ifBlank { subtitle },
    imageUrl = imageUrl,
    priceUsd = retailPriceUsd
)


data class SupplierInvoice(
    val id: String,
    val invoiceNumber: String,
    val supplierName: String,
    val categoryDescription: String,
    val dueDateText: String,
    val pendingBalanceUsd: Double,
    val isCredit: Boolean = true
)

data class TrustedCustomer(
    val id: String,
    val name: String,
    val aliasOrAddress: String,
    val avatarUrl: String,
    val debtUsd: Double,
    val creditLimitUsd: Double,
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
