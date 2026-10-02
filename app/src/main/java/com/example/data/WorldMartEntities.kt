package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "world_mart_staff")
data class StaffEntity(
    @PrimaryKey val employeeId: String, // e.g. WM-EMP-101
    val fullName: String,
    val photoAvatarId: Int = 1,
    val photoPath: String = "",
    val position: String,
    val division: String, // Leadership, Technology, Finance, Operations, Marketing, Business Development, Design, Customer/Seller Support
    val department: String,
    val location: String,
    val assignedDistrict: String = "Central Metro",
    val assignedArea: String = "Commercial Hub",
    val joiningDate: String,
    val employmentStatus: String, // Full-Time, Executive, Contract
    val professionalBiography: String,
    val responsibilities: String,
    val qualification: String,
    val officialWorkEmail: String,
    val officialPhone: String = "+1 (555) 234-5678",
    val manager: String = "Julian Vance (CEO)",
    val accountStatus: String = "Active", // Active, Inactive, On Leave
    // Confidential fields (restricted to Founder/CEO, Super Admin, Finance)
    val salaryGrade: String = "Tier-4 Executive",
    val bankAccountMasked: String = "•••• 8923",
    val nationalIdMasked: String = "ID-•••-4412",
    val internalNotes: String = "Clearance Level 2",
    val isLeadership: Boolean = false
)

@Entity(tableName = "world_mart_shops")
data class ShopEntity(
    @PrimaryKey val shopId: String, // e.g. WM-SHP-201
    val shopName: String,
    val owner: String,
    val category: String,
    val address: String,
    val district: String,
    val block: String = "Block-4",
    val city: String = "Metropolis",
    val pin: String = "400001",
    val contact: String,
    val assignedRepresentative: String,
    val registrationDate: String,
    val status: String = "Active", // Active, Inactive, Pending
    val verificationStatus: String = "Verified", // Verified, Pending, Unverified
    val eshopStatus: String = "Live", // Live, In Setup, Paused
    val sellerId: String = "",
    val documentsCount: Int = 2,
    val paymentsCount: Int = 1,
    val productsCount: Int = 12,
    val notes: String = "Verified merchant location with direct street access."
)

@Entity(tableName = "WORLD_MART_SELLERS")
data class SellerEntity(
    @PrimaryKey val sellerId: String, // e.g. WM-SLR-1001
    val businessName: String,
    val businessCategory: String,
    val businessType: String = "Sole Proprietorship", // Sole Proprietorship, Partnership, Private Limited, LLP
    val ownerName: String,
    val location: String,
    val district: String,
    val block: String = "Block-4",
    val city: String = "Metropolis",
    val contactPhone: String,
    val contactEmail: String,
    val registrationDate: String,
    val assignedRepresentative: String,
    val sellerStatus: String, // Active, Inactive, Suspended
    val verificationStatus: String, // Verified, Pending Documents, Unverified, Under Review
    val eshopStatus: String, // Live, In Setup, Paused
    val agreementStatus: String = "Signed", // Pending, Signed, Expired
    // 11-step commercial workflow
    val workflowStep: String = "Active Seller",
    val productCount: Int = 0,
    val storeRating: Float = 4.8f,
    val monthlyGmv: Double = 14500.0,
    val storefrontDescription: String = "Trusted local vendor offering premium quality items with same-day district delivery.",
    val shopId: String = "",
    val documentsJson: String = "",
    val documentsCount: Int = 2,
    val paymentsCount: Int = 1,
    val notes: String = "High potential district retailer."
)

@Entity(tableName = "world_mart_seller_documents")
data class SellerDocumentEntity(
    @PrimaryKey val documentId: String, // e.g. DOC-WM-1001
    val sellerId: String,
    val documentType: String, // Trade License, GST / Tax ID, Identity Proof, Address Proof, Bank Statement / Cheque
    val filePath: String, // Internal secure storage path
    val uploadedBy: String,
    val uploadDate: String,
    val verificationStatus: String = "PENDING", // PENDING, UNDER REVIEW, VERIFIED, REJECTED
    val verifiedBy: String = "",
    val verificationDate: String = "",
    val rejectionReason: String = ""
)

@Entity(tableName = "world_mart_seller_payments")
data class SellerPaymentEntity(
    @PrimaryKey val paymentId: String, // e.g. PAY-WM-5001
    val sellerId: String,
    val shopId: String,
    val service: String, // Onboarding Fee, Digital E-Shop Setup, Cataloging (50 SKUs), Verification Inspection, Annual Tech Maintenance
    val amount: Double,
    val date: String,
    val paymentMethod: String, // UPI, Cash, Bank Transfer, Escrow, Card
    val transactionRefNumber: String,
    val paymentStatus: String = "PENDING", // PENDING, SUBMITTED, UNDER REVIEW, CONFIRMED, FAILED, REFUNDED
    val receiptPath: String = "",
    val collectedBy: String,
    val verifiedBy: String = "",
    val notes: String = ""
)

@Entity(tableName = "world_mart_service_pricings")
data class ServicePricingEntity(
    @PrimaryKey val serviceId: String, // e.g. SVC-ONBOARDING
    val serviceName: String,
    val category: String,
    val basePrice: Double,
    val district: String = "All Districts",
    val description: String = "",
    val isConfigurable: Boolean = true
)

@Entity(tableName = "world_mart_follow_ups")
data class FollowUpEntity(
    @PrimaryKey(autoGenerate = true) val followUpId: Int = 0,
    val sellerId: String,
    val shopId: String,
    val employeeId: String,
    val employeeName: String,
    val purpose: String, // Document Collection, Payment Clearance, Catalog Onboarding, Premises Inspection, Re-visit
    val dueDate: String,
    val priority: String = "Normal", // Urgent, High, Normal, Low
    val status: String = "PENDING", // PENDING, COMPLETED, CANCELLED
    val notes: String = ""
)

@Entity(tableName = "world_mart_approvals")
data class ApprovalRecordEntity(
    @PrimaryKey(autoGenerate = true) val approvalId: Int = 0,
    val entityType: String, // SELLER, PRODUCT, DOCUMENT, PAYMENT, DAILY_REPORT
    val entityId: String,
    val entityTitle: String,
    val action: String, // APPROVE, REJECT, REQUEST_CHANGES
    val previousStatus: String,
    val newStatus: String,
    val userRole: String,
    val userName: String,
    val timestamp: String,
    val comment: String = ""
)

@Entity(tableName = "world_mart_audit_logs")
data class AuditLogEntity(
    @PrimaryKey(autoGenerate = true) val logId: Int = 0,
    val user: String,
    val userRole: String,
    val action: String, // Seller Created, Seller Approved, Payment Verified, Product Approved, Document Rejected, Employee Assigned, Task Completed
    val recordType: String, // Seller, Shop, Product, Payment, Document, Staff, Task, Pricing
    val recordId: String,
    val timestamp: String,
    val changeDetails: String
)

@Entity(tableName = "world_mart_notifications")
data class AppNotificationEntity(
    @PrimaryKey(autoGenerate = true) val notificationId: Int = 0,
    val title: String,
    val message: String,
    val category: String, // SELLER, DOCUMENT, PAYMENT, PRODUCT, TASK, REPORT, APPROVAL, SYSTEM
    val recipientRole: String = "ALL", // ALL or UserRole name
    val timestamp: String,
    val isRead: Boolean = false,
    val linkedId: String = ""
)

@Entity(tableName = "WORLD_MART_PRODUCTS")
data class ProductEntity(
    @PrimaryKey val productId: String, // e.g. WM-PRD-8001
    val sellerId: String,
    val shopId: String = "",
    val productName: String,
    val category: String,
    val subcategory: String,
    val brand: String = "World Mart Partner",
    val description: String,
    val price: Double,
    val discount: Double = 0.0,
    val stock: Int = 45,
    val sku: String = "SKU-WM-8001",
    val unit: String = "pcs",
    val photos: String = "", // Delimited file paths or URIs
    val photoCode: String = "gadget",
    val status: String = "APPROVED", // DRAFT, PENDING REVIEW, APPROVED, PUBLISHED, OUT OF STOCK, SUSPENDED, ARCHIVED
    val availability: String = "In Stock",
    val createdBy: String = "Marcus Vance (Sales Rep)",
    val createdDate: String = "2026-09-01",
    val updatedDate: String = "2026-10-01",
    val stockQuantity: Int = 45,
    val rating: Float = 4.9f
)

@Entity(tableName = "world_mart_product_photos")
data class ProductPhotoEntity(
    @PrimaryKey(autoGenerate = true) val photoId: Int = 0,
    val sellerId: String,
    val shopId: String,
    val productId: String,
    val uploader: String,
    val date: String,
    val filePath: String, // Persistent internal storage path
    val caption: String = "",
    val status: String = "Approved", // Approved, Pending, Rejected
    val rejectionReason: String = ""
)

@Entity(tableName = "world_mart_daily_reports")
data class DailyReportEntity(
    @PrimaryKey(autoGenerate = true) val reportId: Int = 0,
    val repId: String,
    val repName: String,
    val date: String,
    val district: String,
    val area: String,
    val shopsPlanned: Int,
    val shopsVisited: Int,
    val newLeads: Int,
    val interestedShops: Int,
    val newSellers: Int,
    val documentsCollected: Int,
    val paymentsSubmitted: Double = 0.0,
    val productsCollected: Int,
    val photosCollected: Int,
    val followUps: String,
    val problems: String,
    val tomorrowPlan: String,
    val notes: String,
    val reviewStatus: String = "Submitted", // Submitted, Reviewed, Action Required, Approved
    val managerReviewer: String = "",
    val managerFeedback: String = ""
)

@Entity(tableName = "world_mart_shop_visits")
data class ShopVisitEntity(
    @PrimaryKey(autoGenerate = true) val visitId: Int = 0,
    val shopId: String,
    val sellerId: String,
    val shopName: String,
    val repName: String,
    val date: String,
    val time: String,
    val purpose: String, // Onboarding, Catalog Check, Photo Session, Dispute, Routine, Payment Collection, Document Collection
    val discussion: String = "",
    val registrationStatus: String = "Completed",
    val documentsCollected: Int = 0,
    val productsCollected: Int = 0,
    val photosCollected: Int = 0,
    val followUpRequired: Boolean = false,
    val nextFollowUpDate: String = "",
    val outcome: String,
    val notes: String
)

@Entity(tableName = "world_mart_user_accounts")
data class UserAccountEntity(
    @PrimaryKey val userId: String,
    val username: String,
    val passwordHash: String,
    val email: String,
    val fullName: String,
    val roleName: String, // Matches UserRole.name
    val phone: String = "",
    val linkedSellerId: String = "",
    val linkedShopId: String = "",
    val linkedEmployeeId: String = "",
    val status: String = "Active",
    val createdAt: String = "2026-10-01"
)

@Entity(tableName = "world_mart_orders")
data class OrderEntity(
    @PrimaryKey val orderId: String,
    val customerId: String,
    val customerName: String,
    val customerEmail: String,
    val customerPhone: String,
    val shippingAddress: String,
    val district: String,
    val totalAmount: Double,
    val paymentMethod: String = "WorldMart Pay Escrow",
    val paymentStatus: String = "Paid",
    val orderStatus: String = "Confirmed",
    val orderDate: String = "2026-10-01",
    val estimatedDelivery: String = "Within 2 Hours",
    val itemsSummary: String = ""
)

@Entity(tableName = "world_mart_cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val cartItemId: Int = 0,
    val customerId: String,
    val productId: String,
    val productName: String,
    val price: Double,
    val quantity: Int = 1,
    val sellerId: String = "",
    val shopId: String = "",
    val photoCode: String = "gadget"
)

@Entity(tableName = "world_mart_announcements")
data class AnnouncementEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val category: String,
    val date: String,
    val summary: String,
    val content: String,
    val isPublic: Boolean = true,
    val authorRole: String = "Communications Team"
)

@Entity(tableName = "world_mart_tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val assignedDivision: String,
    val district: String = "Central Metro",
    val shopId: String = "",
    val sellerId: String = "",
    val priority: String, // Urgent, High, Normal, Low
    val dueDate: String,
    val isCompleted: Boolean = false,
    val status: String = "PENDING", // PENDING, IN_PROGRESS, COMPLETED, CANCELLED
    val assignedBy: String = "Management",
    val assignedToUser: String = ""
)

@Entity(tableName = "world_mart_content_blocks")
data class ContentBlockEntity(
    @PrimaryKey val key: String,
    val title: String,
    val subtitle: String,
    val body: String,
    val lastUpdated: String = "2026-10-01",
    val updatedBy: String = "System Admin"
)

@Entity(tableName = "world_mart_jobs")
data class JobEntity(
    @PrimaryKey val jobId: String,
    val title: String,
    val division: String,
    val location: String,
    val employmentType: String,
    val experienceLevel: String,
    val overview: String,
    val requirements: String,
    val isOpen: Boolean = true
)

@Entity(tableName = "world_mart_job_applications")
data class JobApplicationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val jobId: String,
    val jobTitle: String,
    val applicantName: String,
    val applicantEmail: String,
    val applicantPhone: String,
    val qualifications: String,
    val portfolioUrl: String,
    val submittedAt: String,
    val status: String = "Under Review"
)

@Entity(tableName = "world_mart_inquiries")
data class InquiryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val email: String,
    val category: String,
    val subject: String,
    val message: String,
    val submittedAt: String,
    val status: String = "New"
)

@Entity(tableName = "world_mart_financials")
data class FinancialRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val transactionRef: String,
    val title: String,
    val category: String,
    val amount: Double,
    val type: String,
    val date: String,
    val district: String
)
