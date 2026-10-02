package com.example.model

enum class Division(val displayName: String, val iconName: String) {
    LEADERSHIP("Leadership", "shield"),
    TECHNOLOGY("Technology", "code"),
    FINANCE("Finance", "payments"),
    OPERATIONS("Operations", "local_shipping"),
    MARKETING("Marketing", "campaign"),
    BUSINESS_DEVELOPMENT("Business Development", "trending_up"),
    DESIGN("Design", "palette"),
    CUSTOMER_SUPPORT("Customer/Seller Support", "support_agent");

    companion object {
        fun fromString(name: String): Division {
            return entries.find { it.name.equals(name, ignoreCase = true) || it.displayName.equals(name, ignoreCase = true) }
                ?: OPERATIONS
        }
    }
}

enum class SellerWorkflowStep(val stepNumber: Int, val label: String, val description: String) {
    LEAD(1, "Lead", "Identified potential merchant"),
    CONTACTED(2, "Contacted", "Initial field or phone contact established"),
    INTERESTED(3, "Interested", "Merchant agreed to review World Mart partnership"),
    REGISTRATION_STARTED(4, "Registration Started", "Store details, category & district submitted"),
    DOCUMENTS_PENDING(5, "Documents Pending", "Awaiting trade license and identity documents"),
    DOCUMENTS_SUBMITTED(6, "Documents Submitted", "Commercial documents uploaded to verification queue"),
    VERIFICATION_PENDING(7, "Verification Pending", "Physical premises inspection scheduled with field rep"),
    VERIFIED(8, "Verified", "Compliance verified & approved for digital storefront"),
    ESHOP_PREPARATION(9, "E-shop Preparation", "Digital storefront design, hours & banner configured"),
    PRODUCT_LISTING(10, "Product Listing", "Initial catalog SKUs and photos uploaded"),
    ACTIVE_SELLER(11, "Active Seller", "Live on World Mart marketplace receiving district orders");

    companion object {
        fun fromString(value: String): SellerWorkflowStep {
            return entries.find { it.name.equals(value, ignoreCase = true) || it.label.equals(value, ignoreCase = true) }
                ?: LEAD
        }
    }
}

enum class DocumentVerificationStatus(val label: String) {
    PENDING("PENDING"),
    UNDER_REVIEW("UNDER REVIEW"),
    VERIFIED("VERIFIED"),
    REJECTED("REJECTED")
}

enum class PaymentStatus(val label: String) {
    PENDING("PENDING"),
    SUBMITTED("SUBMITTED"),
    UNDER_REVIEW("UNDER REVIEW"),
    CONFIRMED("CONFIRMED"),
    FAILED("FAILED"),
    REFUNDED("REFUNDED")
}

enum class ProductStatus(val label: String) {
    DRAFT("DRAFT"),
    PENDING_REVIEW("PENDING REVIEW"),
    APPROVED("APPROVED"),
    PUBLISHED("PUBLISHED"),
    OUT_OF_STOCK("OUT OF STOCK"),
    SUSPENDED("SUSPENDED"),
    ARCHIVED("ARCHIVED")
}

enum class FollowUpStatus(val label: String) {
    PENDING("PENDING"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED")
}

enum class UserRole(
    val title: String,
    val description: String,
    val defaultUsername: String,
    val defaultEmail: String
) {
    FOUNDER_CEO(
        title = "Founder / CEO",
        description = "Full unrestricted executive control across all company operations",
        defaultUsername = "ceo.admin",
        defaultEmail = "ceo@worldmart.com"
    ),
    SUPER_ADMIN(
        title = "Super Admin",
        description = "Enterprise administrative control, user provisioning & full audit",
        defaultUsername = "super.admin",
        defaultEmail = "superadmin@worldmart.com"
    ),
    OPERATIONS(
        title = "Operations Manager",
        description = "Seller, shop, district logistics, field visits & verification records",
        defaultUsername = "ops.manager",
        defaultEmail = "operations@worldmart.com"
    ),
    FINANCE(
        title = "Finance Manager",
        description = "Seller payment verification, ledger records, refunds & pricing configurations",
        defaultUsername = "finance.lead",
        defaultEmail = "finance@worldmart.com"
    ),
    TECHNOLOGY(
        title = "Technology Manager",
        description = "Technical, database, APIs, security & operations infrastructure",
        defaultUsername = "tech.admin",
        defaultEmail = "tech@worldmart.com"
    ),
    MARKETING(
        title = "Marketing Manager",
        description = "Marketing campaigns, announcements & seller acquisition materials",
        defaultUsername = "marketing.lead",
        defaultEmail = "marketing@worldmart.com"
    ),
    BUSINESS_DEVELOPMENT(
        title = "Business Development",
        description = "Merchant partnerships, trade chambers & enterprise sellers",
        defaultUsername = "bizdev.lead",
        defaultEmail = "bizdev@worldmart.com"
    ),
    DESIGN(
        title = "Design Lead",
        description = "Storefront design themes, catalogue standards & brand assets",
        defaultUsername = "design.lead",
        defaultEmail = "design@worldmart.com"
    ),
    CUSTOMER_SUPPORT(
        title = "Customer/Seller Support",
        description = "Merchant helpdesk, support inquiries & seller disputes",
        defaultUsername = "support.lead",
        defaultEmail = "support@worldmart.com"
    ),
    SALES_REP(
        title = "Sales Representative",
        description = "Mobile field workspace: assigned shops, visits, KYC docs, photos & daily reports",
        defaultUsername = "rep.marcus",
        defaultEmail = "m.vance@worldmart.com"
    ),
    SELLER(
        title = "Seller Management",
        description = "Manage assigned shop catalog, KYC docs, payments & orders",
        defaultUsername = "seller.apex",
        defaultEmail = "seller@apexdigital.shop"
    ),
    CUSTOMER(
        title = "Internal Auditor",
        description = "Quality review & operations compliance oversight",
        defaultUsername = "auditor.internal",
        defaultEmail = "audit@worldmart.com"
    ),
    GUEST(
        title = "Public Guest",
        description = "Public corporate portal visitor",
        defaultUsername = "guest",
        defaultEmail = "visitor@worldmart.com"
    );

    fun isFounderOrSuperAdmin(): Boolean = this in listOf(FOUNDER_CEO, SUPER_ADMIN)
    fun canAccessStaff(): Boolean = this in listOf(FOUNDER_CEO, SUPER_ADMIN, TECHNOLOGY, OPERATIONS)
    fun canAccessConfidentialStaffData(): Boolean = this in listOf(FOUNDER_CEO, SUPER_ADMIN, FINANCE)
    fun canAccessSellers(): Boolean = this in listOf(FOUNDER_CEO, SUPER_ADMIN, OPERATIONS, SALES_REP, TECHNOLOGY, BUSINESS_DEVELOPMENT, SELLER)
    fun canAccessFinance(): Boolean = this in listOf(FOUNDER_CEO, SUPER_ADMIN, FINANCE)
    fun canAccessCMS(): Boolean = this in listOf(FOUNDER_CEO, SUPER_ADMIN, TECHNOLOGY, MARKETING, DESIGN)
    fun canAccessProducts(): Boolean = this in listOf(FOUNDER_CEO, SUPER_ADMIN, OPERATIONS, SALES_REP, TECHNOLOGY, SELLER, DESIGN)
    fun canAccessTasks(): Boolean = this != GUEST
    fun canAccessDailyReports(): Boolean = this in listOf(FOUNDER_CEO, SUPER_ADMIN, OPERATIONS, SALES_REP)
    fun canReviewReports(): Boolean = this in listOf(FOUNDER_CEO, SUPER_ADMIN, OPERATIONS)
    fun canApproveSellers(): Boolean = this in listOf(FOUNDER_CEO, SUPER_ADMIN, OPERATIONS)
    fun canVerifyDocuments(): Boolean = this in listOf(FOUNDER_CEO, SUPER_ADMIN, OPERATIONS)
    fun canVerifyPayments(): Boolean = this in listOf(FOUNDER_CEO, SUPER_ADMIN, FINANCE)
    fun canApproveProducts(): Boolean = this in listOf(FOUNDER_CEO, SUPER_ADMIN, OPERATIONS, DESIGN)
    fun canConfigurePricing(): Boolean = this in listOf(FOUNDER_CEO, SUPER_ADMIN, FINANCE)
    fun canViewAuditLogs(): Boolean = this in listOf(FOUNDER_CEO, SUPER_ADMIN, TECHNOLOGY)
    fun isSalesRep(): Boolean = this == SALES_REP
    fun isSeller(): Boolean = this == SELLER
}
