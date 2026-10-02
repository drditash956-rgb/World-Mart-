package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        StaffEntity::class,
        ShopEntity::class,
        SellerEntity::class,
        ProductEntity::class,
        ProductPhotoEntity::class,
        DailyReportEntity::class,
        ShopVisitEntity::class,
        UserAccountEntity::class,
        OrderEntity::class,
        CartItemEntity::class,
        AnnouncementEntity::class,
        TaskEntity::class,
        ContentBlockEntity::class,
        JobEntity::class,
        JobApplicationEntity::class,
        InquiryEntity::class,
        FinancialRecordEntity::class,
        SellerDocumentEntity::class,
        SellerPaymentEntity::class,
        ServicePricingEntity::class,
        FollowUpEntity::class,
        ApprovalRecordEntity::class,
        AuditLogEntity::class,
        AppNotificationEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class WorldMartDatabase : RoomDatabase() {
    abstract fun worldMartDao(): WorldMartDao

    companion object {
        @Volatile
        private var INSTANCE: WorldMartDatabase? = null

        fun getDatabase(context: Context): WorldMartDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorldMartDatabase::class.java,
                    "world_mart_corporate_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
