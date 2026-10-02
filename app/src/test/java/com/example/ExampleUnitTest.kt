package com.example

import com.example.data.SecurityUtils
import com.example.model.Division
import com.example.model.SellerWorkflowStep
import com.example.model.UserRole
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testPasswordHashingAndVerification() {
        val rawPassword = "SecurePassword@2026"
        val hash = SecurityUtils.hashPassword(rawPassword)
        assertNotNull(hash)
        assertTrue(hash.length == 64) // SHA-256 hex string

        // Verify password
        assertTrue(SecurityUtils.verifyPassword(rawPassword, hash))
        assertFalse(SecurityUtils.verifyPassword("WrongPassword", hash))
    }

    @Test
    fun testUserRolePermissions() {
        // Founder / CEO has access to everything
        val ceo = UserRole.FOUNDER_CEO
        assertTrue(ceo.canAccessStaff())
        assertTrue(ceo.canAccessConfidentialStaffData())
        assertTrue(ceo.canAccessSellers())
        assertTrue(ceo.canAccessFinance())
        assertTrue(ceo.canAccessCMS())
        assertTrue(ceo.canAccessProducts())
        assertTrue(ceo.canReviewReports())

        // Sales Rep access
        val salesRep = UserRole.SALES_REP
        assertFalse(salesRep.canAccessStaff())
        assertFalse(salesRep.canAccessConfidentialStaffData())
        assertTrue(salesRep.canAccessSellers())
        assertFalse(salesRep.canAccessFinance())
        assertTrue(salesRep.canAccessDailyReports())
        assertFalse(salesRep.canReviewReports())

        // Finance access
        val finance = UserRole.FINANCE
        assertTrue(finance.canAccessFinance())
        assertTrue(finance.canAccessConfidentialStaffData())
        assertFalse(finance.canAccessProducts())
    }

    @Test
    fun testSellerWorkflowSteps() {
        assertEquals(11, SellerWorkflowStep.entries.size)
        assertEquals("Lead", SellerWorkflowStep.LEAD.label)
        assertEquals(1, SellerWorkflowStep.LEAD.stepNumber)

        assertEquals("Active Seller", SellerWorkflowStep.ACTIVE_SELLER.label)
        assertEquals(11, SellerWorkflowStep.ACTIVE_SELLER.stepNumber)

        val parsed = SellerWorkflowStep.fromString("Documents Pending")
        assertEquals(SellerWorkflowStep.DOCUMENTS_PENDING, parsed)
    }

    @Test
    fun testDivisionMapping() {
        assertEquals(7, Division.entries.size)
        assertEquals(Division.TECHNOLOGY, Division.fromString("Technology"))
        assertEquals(Division.OPERATIONS, Division.fromString("Operations"))
    }
}
