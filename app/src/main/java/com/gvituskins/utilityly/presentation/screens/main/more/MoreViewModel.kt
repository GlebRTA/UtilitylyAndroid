package com.gvituskins.utilityly.presentation.screens.main.more

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.data.preferences.DataStoreUtil
import com.gvituskins.utilityly.domain.models.categories.Category
import com.gvituskins.utilityly.domain.models.categories.CategoryParameter
import com.gvituskins.utilityly.domain.models.companies.Company
import com.gvituskins.utilityly.domain.models.enums.PaidStatus
import com.gvituskins.utilityly.domain.models.enums.ThemeType
import com.gvituskins.utilityly.domain.models.locations.Location
import com.gvituskins.utilityly.domain.models.utilities.Utility
import com.gvituskins.utilityly.domain.repositories.CategoryRepository
import com.gvituskins.utilityly.domain.repositories.CompanyRepository
import com.gvituskins.utilityly.domain.repositories.LocationRepository
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val dataStore: DataStoreUtil,
    private val locationRepository: LocationRepository,
    private val companyRepository: CompanyRepository,
    private val categoryRepository: CategoryRepository,
    private val utilityRepository: UtilityRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        MoreState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        dataStore
            .currentTheme()
            .onEach { newTheme ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(theme = newTheme)
                }
            }
            .launchIn(viewModelScope)
    }

    fun changeThemeSection(isOpened: Boolean) {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                themSectionOpened = isOpened
            )
        }
    }

    fun updateTheme(type: ThemeType) {
        viewModelScope.launch {
            dataStore.changeTheme(type)
            _uiState.update { currentUiState ->
                currentUiState.copy(
                    theme = type
                )
            }
        }
    }

    fun fillBaseStagingData() {
        viewModelScope.launch {
            addLocations()
            addCompanies()
            addCategories()
        }
    }

    fun addUtilitiesStagingData() {
        viewModelScope.launch { addUtilities() }
    }

    private suspend fun addLocations() {
        locationRepository.addNewLocation(Location(id = 0, name = "Garage"))
        locationRepository.addNewLocation(Location(id = 0, name = "Country House"))
    }

    private suspend fun addCompanies() {
        val baseCompany = Company(
            id = 0,
            name = "Input",
            address = null,
            phone = null,
            webPageUrl = null,
            email = null
        )
        companyRepository.addCompany(baseCompany.copy(name = "Tet"))
        companyRepository.addCompany(baseCompany.copy(name = "Electrum"))
        companyRepository.addCompany(baseCompany.copy(name = "Rezekne Water"))
        companyRepository.addCompany(baseCompany.copy(name = "LPG"))
    }

    private suspend fun addCategories() {
        val baseCategory = Category(
            id = 0,
            name = "Input",
            description = null,
            color = Color.Red,
            parameters = listOf()
        )

        categoryRepository.addNewCategory(
            baseCategory.copy(
                name = "Water",
                color = Color.Blue,
                parameters = listOf(
                    CategoryParameter(id = 0, name = "Kitchen Cold"),
                    CategoryParameter(id = 0, name = "Kitchen Hot"),
                    CategoryParameter(id = 0, name = "Bathroom Cold"),
                    CategoryParameter(id = 0, name = "Bathroom Hot"),
                )
            )
        )

        categoryRepository.addNewCategory(
            baseCategory.copy(
                name = "Electricity",
                color = Color.Yellow
            )
        )
        categoryRepository.addNewCategory(
            baseCategory.copy(
                name = "Heating",
                color = Color(0xffFFA500)
            )
        )
        categoryRepository.addNewCategory(
            baseCategory.copy(
                name = "Gas",
                description = "Town gas",
                color = Color.Green
            )
        )
    }

    private suspend fun addUtilities() {
        val baseUtility = Utility(
            id = 0,
            category = Category(
                id = 1,
                name = "",
                description = null,
                color = Color.Red,
                parameters = listOf()
            ),
            company = Company(id = 3, "", null, null, null, null),
            repeat = null,
            amount = 12.0,
            location = Location(
                id = 1,
                name = ""
            ),
            dateCreated = LocalDate.now(),
            paidStatus = PaidStatus.UNPAID,
            dueDate = LocalDate.of(2024, 2, 12),
            datePaid = null,
        )

        //For location 1
        repeat(50) {
            utilityRepository.addNewUtility(
                baseUtility.copy(
                    amount = Random.nextInt(5, 100).toDouble(),
                    paidStatus = randomPaidStatus(),
                    dueDate = LocalDate.of(Random.nextInt(2023, 2026), Random.nextInt(1, 13), Random.nextInt(1, 29)),
                )
            )
        }

        repeat(50) {
            utilityRepository.addNewUtility(
                baseUtility.copy(
                    category = baseUtility.category.copy(id = 2),
                    amount = Random.nextInt(50, 350).toDouble(),
                    paidStatus = randomPaidStatus(),
                    dueDate = LocalDate.of(Random.nextInt(2023, 2026), Random.nextInt(1, 13), Random.nextInt(1, 29)),
                )
            )
        }

        repeat(50) {
            utilityRepository.addNewUtility(
                baseUtility.copy(
                    category = baseUtility.category.copy(id = 3),
                    amount = Random.nextInt(75, 200).toDouble(),
                    paidStatus = randomPaidStatus(),
                    dueDate = LocalDate.of(Random.nextInt(2023, 2026), Random.nextInt(1, 13), Random.nextInt(1, 29))
                )
            )
        }

        repeat(50) {
            utilityRepository.addNewUtility(
                baseUtility.copy(
                    category = baseUtility.category.copy(id = 4),
                    amount = Random.nextInt(5, 60).toDouble(),
                    paidStatus = randomPaidStatus(),
                    dueDate = LocalDate.of(Random.nextInt(2023, 2026), Random.nextInt(1, 13), Random.nextInt(1, 29))
                )
            )
        }

        //For location 2
        repeat(25) {
            utilityRepository.addNewUtility(
                baseUtility.copy(
                    location = baseUtility.location.copy(id = 2),
                    category = baseUtility.category.copy(id = 2),
                    amount = Random.nextInt(30, 170).toDouble(),
                    paidStatus = randomPaidStatus(),
                    dueDate = LocalDate.of(Random.nextInt(2023, 2026), Random.nextInt(1, 13), Random.nextInt(1, 29))
                )
            )
        }

        repeat(25) {
            utilityRepository.addNewUtility(
                baseUtility.copy(
                    location = baseUtility.location.copy(id = 2),
                    amount = Random.nextInt(30, 170).toDouble(),
                    paidStatus = randomPaidStatus(),
                    dueDate = LocalDate.of(Random.nextInt(2023, 2026), Random.nextInt(1, 13), Random.nextInt(1, 29))
                )
            )
        }
    }

    private fun randomPaidStatus(): PaidStatus {
        return when (Random.nextBoolean()) {
            true -> PaidStatus.PAID
            false -> PaidStatus.UNPAID
        }
    }
}

data class MoreState(
    val themSectionOpened: Boolean = false,
    val theme: ThemeType = ThemeType.SYSTEM,
)
