package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WorldMartDao {

    // --- Users & Authentication ---
    @Query("SELECT * FROM world_mart_user_accounts WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserAccountEntity?

    @Query("SELECT * FROM world_mart_user_accounts WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserAccountEntity?

    @Query("SELECT * FROM world_mart_user_accounts WHERE userId = :userId LIMIT 1")
    suspend fun getUserById(userId: String): UserAccountEntity?

    @Query("SELECT * FROM world_mart_user_accounts ORDER BY createdAt DESC")
    fun getAllUsers(): Flow<List<UserAccountEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserAccountEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users: List<UserAccountEntity>)

    @Update
    suspend fun updateUser(user: UserAccountEntity)

    // --- Staff ---
    @Query("SELECT * FROM world_mart_staff ORDER BY employeeId ASC")
    fun getAllStaff(): Flow<List<StaffEntity>>

    @Query("SELECT * FROM world_mart_staff WHERE division = :division ORDER BY employeeId ASC")
    fun getStaffByDivision(division: String): Flow<List<StaffEntity>>

    @Query("SELECT * FROM world_mart_staff WHERE employeeId = :empId LIMIT 1")
    suspend fun getStaffById(empId: String): StaffEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStaff(staff: StaffEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStaff(staffList: List<StaffEntity>)

    @Update
    suspend fun updateStaff(staff: StaffEntity)

    @Delete
    suspend fun deleteStaff(staff: StaffEntity)

    // --- Shops ---
    @Query("SELECT * FROM world_mart_shops ORDER BY registrationDate DESC")
    fun getAllShops(): Flow<List<ShopEntity>>

    @Query("SELECT * FROM world_mart_shops WHERE assignedRepresentative = :repName ORDER BY registrationDate DESC")
    fun getShopsByRep(repName: String): Flow<List<ShopEntity>>

    @Query("SELECT * FROM world_mart_shops WHERE district = :district ORDER BY shopName ASC")
    fun getShopsByDistrict(district: String): Flow<List<ShopEntity>>

    @Query("SELECT * FROM world_mart_shops WHERE shopId = :shopId LIMIT 1")
    suspend fun getShopById(shopId: String): ShopEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShop(shop: ShopEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllShops(shops: List<ShopEntity>)

    @Update
    suspend fun updateShop(shop: ShopEntity)

    @Delete
    suspend fun deleteShop(shop: ShopEntity)

    // --- Sellers ---
    @Query("SELECT * FROM WORLD_MART_SELLERS ORDER BY registrationDate DESC")
    fun getAllSellers(): Flow<List<SellerEntity>>

    @Query("SELECT * FROM WORLD_MART_SELLERS WHERE assignedRepresentative = :repName ORDER BY registrationDate DESC")
    fun getSellersByRep(repName: String): Flow<List<SellerEntity>>

    @Query("SELECT * FROM WORLD_MART_SELLERS WHERE sellerId = :sellerId LIMIT 1")
    suspend fun getSellerById(sellerId: String): SellerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeller(seller: SellerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSellers(sellers: List<SellerEntity>)

    @Update
    suspend fun updateSeller(seller: SellerEntity)

    @Delete
    suspend fun deleteSeller(seller: SellerEntity)

    // --- Seller Documents ---
    @Query("SELECT * FROM world_mart_seller_documents ORDER BY uploadDate DESC")
    fun getAllSellerDocuments(): Flow<List<SellerDocumentEntity>>

    @Query("SELECT * FROM world_mart_seller_documents WHERE sellerId = :sellerId ORDER BY uploadDate DESC")
    fun getDocumentsForSeller(sellerId: String): Flow<List<SellerDocumentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(document: SellerDocumentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDocuments(documents: List<SellerDocumentEntity>)

    @Update
    suspend fun updateDocument(document: SellerDocumentEntity)

    @Delete
    suspend fun deleteDocument(document: SellerDocumentEntity)

    // --- Seller Payments ---
    @Query("SELECT * FROM world_mart_seller_payments ORDER BY date DESC")
    fun getAllSellerPayments(): Flow<List<SellerPaymentEntity>>

    @Query("SELECT * FROM world_mart_seller_payments WHERE sellerId = :sellerId ORDER BY date DESC")
    fun getPaymentsForSeller(sellerId: String): Flow<List<SellerPaymentEntity>>

    @Query("SELECT * FROM world_mart_seller_payments WHERE shopId = :shopId ORDER BY date DESC")
    fun getPaymentsForShop(shopId: String): Flow<List<SellerPaymentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: SellerPaymentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPayments(payments: List<SellerPaymentEntity>)

    @Update
    suspend fun updatePayment(payment: SellerPaymentEntity)

    @Delete
    suspend fun deletePayment(payment: SellerPaymentEntity)

    // --- Service Pricing Configuration ---
    @Query("SELECT * FROM world_mart_service_pricings ORDER BY serviceName ASC")
    fun getAllServicePricings(): Flow<List<ServicePricingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPricing(pricing: ServicePricingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPricings(pricings: List<ServicePricingEntity>)

    @Update
    suspend fun updatePricing(pricing: ServicePricingEntity)

    // --- Follow-ups ---
    @Query("SELECT * FROM world_mart_follow_ups ORDER BY dueDate ASC, priority DESC")
    fun getAllFollowUps(): Flow<List<FollowUpEntity>>

    @Query("SELECT * FROM world_mart_follow_ups WHERE employeeId = :empId OR employeeName = :empName ORDER BY dueDate ASC")
    fun getFollowUpsForEmployee(empId: String, empName: String): Flow<List<FollowUpEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowUp(followUp: FollowUpEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFollowUps(followUps: List<FollowUpEntity>)

    @Update
    suspend fun updateFollowUp(followUp: FollowUpEntity)

    @Delete
    suspend fun deleteFollowUp(followUp: FollowUpEntity)

    // --- Approvals ---
    @Query("SELECT * FROM world_mart_approvals ORDER BY timestamp DESC")
    fun getAllApprovals(): Flow<List<ApprovalRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApproval(approval: ApprovalRecordEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllApprovals(approvals: List<ApprovalRecordEntity>)

    // --- Audit Logs ---
    @Query("SELECT * FROM world_mart_audit_logs ORDER BY timestamp DESC")
    fun getAllAuditLogs(): Flow<List<AuditLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditLog(log: AuditLogEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAuditLogs(logs: List<AuditLogEntity>)

    // --- Notifications ---
    @Query("SELECT * FROM world_mart_notifications ORDER BY timestamp DESC")
    fun getAllNotifications(): Flow<List<AppNotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: AppNotificationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNotifications(notifications: List<AppNotificationEntity>)

    @Query("UPDATE world_mart_notifications SET isRead = 1 WHERE notificationId = :id")
    suspend fun markNotificationRead(id: Int)

    // --- Products ---
    @Query("SELECT * FROM WORLD_MART_PRODUCTS ORDER BY productId ASC")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM WORLD_MART_PRODUCTS WHERE sellerId = :sellerId ORDER BY productId ASC")
    fun getProductsBySeller(sellerId: String): Flow<List<ProductEntity>>

    @Query("SELECT * FROM WORLD_MART_PRODUCTS WHERE shopId = :shopId ORDER BY productId ASC")
    fun getProductsByShop(shopId: String): Flow<List<ProductEntity>>

    @Query("SELECT * FROM WORLD_MART_PRODUCTS WHERE productId = :productId LIMIT 1")
    suspend fun getProductById(productId: String): ProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<ProductEntity>)

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    // --- Product Photos ---
    @Query("SELECT * FROM world_mart_product_photos ORDER BY photoId DESC")
    fun getAllProductPhotos(): Flow<List<ProductPhotoEntity>>

    @Query("SELECT * FROM world_mart_product_photos WHERE productId = :productId ORDER BY photoId DESC")
    fun getPhotosForProduct(productId: String): Flow<List<ProductPhotoEntity>>

    @Query("SELECT * FROM world_mart_product_photos WHERE sellerId = :sellerId ORDER BY photoId DESC")
    fun getPhotosForSeller(sellerId: String): Flow<List<ProductPhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: ProductPhotoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPhotos(photos: List<ProductPhotoEntity>)

    @Update
    suspend fun updatePhoto(photo: ProductPhotoEntity)

    @Delete
    suspend fun deletePhoto(photo: ProductPhotoEntity)

    // --- Daily Staff Reports ---
    @Query("SELECT * FROM world_mart_daily_reports ORDER BY date DESC, reportId DESC")
    fun getAllDailyReports(): Flow<List<DailyReportEntity>>

    @Query("SELECT * FROM world_mart_daily_reports WHERE repId = :repId OR repName = :repName ORDER BY date DESC")
    fun getDailyReportsByRep(repId: String, repName: String): Flow<List<DailyReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyReport(report: DailyReportEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDailyReports(reports: List<DailyReportEntity>)

    @Update
    suspend fun updateDailyReport(report: DailyReportEntity)

    // --- Shop Visits ---
    @Query("SELECT * FROM world_mart_shop_visits ORDER BY date DESC, visitId DESC")
    fun getAllShopVisits(): Flow<List<ShopVisitEntity>>

    @Query("SELECT * FROM world_mart_shop_visits WHERE shopId = :shopId ORDER BY date DESC")
    fun getVisitsForShop(shopId: String): Flow<List<ShopVisitEntity>>

    @Query("SELECT * FROM world_mart_shop_visits WHERE repName = :repName ORDER BY date DESC")
    fun getVisitsByRep(repName: String): Flow<List<ShopVisitEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShopVisit(visit: ShopVisitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllShopVisits(visits: List<ShopVisitEntity>)

    // --- Orders ---
    @Query("SELECT * FROM world_mart_orders ORDER BY orderDate DESC, orderId DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM world_mart_orders WHERE customerId = :customerId ORDER BY orderDate DESC")
    fun getOrdersByCustomer(customerId: String): Flow<List<OrderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Update
    suspend fun updateOrder(order: OrderEntity)

    // --- Cart ---
    @Query("SELECT * FROM world_mart_cart_items WHERE customerId = :customerId")
    fun getCartItems(customerId: String): Flow<List<CartItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItemEntity)

    @Update
    suspend fun updateCartItem(item: CartItemEntity)

    @Delete
    suspend fun deleteCartItem(item: CartItemEntity)

    @Query("DELETE FROM world_mart_cart_items WHERE customerId = :customerId")
    suspend fun clearCart(customerId: String)

    // --- Announcements ---
    @Query("SELECT * FROM world_mart_announcements ORDER BY date DESC, id DESC")
    fun getAllAnnouncements(): Flow<List<AnnouncementEntity>>

    @Query("SELECT * FROM world_mart_announcements WHERE isPublic = 1 ORDER BY date DESC, id DESC")
    fun getPublicAnnouncements(): Flow<List<AnnouncementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnnouncement(announcement: AnnouncementEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAnnouncements(announcements: List<AnnouncementEntity>)

    // --- Tasks ---
    @Query("SELECT * FROM world_mart_tasks ORDER BY isCompleted ASC, priority DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTasks(tasks: List<TaskEntity>)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    // --- Content Blocks (CMS) ---
    @Query("SELECT * FROM world_mart_content_blocks")
    fun getAllContentBlocks(): Flow<List<ContentBlockEntity>>

    @Query("SELECT * FROM world_mart_content_blocks WHERE `key` = :key LIMIT 1")
    suspend fun getContentBlockByKey(key: String): ContentBlockEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContentBlock(block: ContentBlockEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllContentBlocks(blocks: List<ContentBlockEntity>)

    // --- Jobs & Applications ---
    @Query("SELECT * FROM world_mart_jobs WHERE isOpen = 1")
    fun getAllJobs(): Flow<List<JobEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllJobs(jobs: List<JobEntity>)

    @Query("SELECT * FROM world_mart_job_applications ORDER BY id DESC")
    fun getAllJobApplications(): Flow<List<JobApplicationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobApplication(app: JobApplicationEntity)

    // --- Inquiries ---
    @Query("SELECT * FROM world_mart_inquiries ORDER BY id DESC")
    fun getAllInquiries(): Flow<List<InquiryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInquiry(inquiry: InquiryEntity)

    // --- Financial Records ---
    @Query("SELECT * FROM world_mart_financials ORDER BY id DESC")
    fun getAllFinancials(): Flow<List<FinancialRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFinancials(records: List<FinancialRecordEntity>)

    // --- Database Reset & Production Purge Queries ---
    @Query("DELETE FROM world_mart_staff")
    suspend fun deleteAllStaff()

    @Query("DELETE FROM world_mart_shops")
    suspend fun deleteAllShops()

    @Query("DELETE FROM world_mart_sellers")
    suspend fun deleteAllSellers()

    @Query("DELETE FROM world_mart_products")
    suspend fun deleteAllProducts()

    @Query("DELETE FROM world_mart_daily_reports")
    suspend fun deleteAllDailyReports()

    @Query("DELETE FROM world_mart_orders")
    suspend fun deleteAllOrders()

    @Query("DELETE FROM world_mart_tasks")
    suspend fun deleteAllTasks()

    @Query("DELETE FROM world_mart_seller_documents")
    suspend fun deleteAllSellerDocuments()

    @Query("DELETE FROM world_mart_seller_payments")
    suspend fun deleteAllSellerPayments()

    @Query("DELETE FROM world_mart_follow_ups")
    suspend fun deleteAllFollowUps()

    @Query("DELETE FROM world_mart_approvals")
    suspend fun deleteAllApprovals()

    @Query("DELETE FROM world_mart_shop_visits")
    suspend fun deleteAllShopVisits()
}
