package com.gvituskins.utilityly.presentation.screens.main.locations

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.data.preferences.DataStoreUtil
import com.gvituskins.utilityly.domain.models.locations.Location
import com.gvituskins.utilityly.domain.repositories.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val preferences: DataStoreUtil,
    private val locationRepository: LocationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationsState())
    val uiState = _uiState.asStateFlow()

    private val _label = Channel<LocationOTE>()
    val label = _label.receiveAsFlow()

    init {
        preferences.locationId()
            .onEach { newLocationId ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(selectedLocationId = newLocationId)
                }
            }
            .launchIn(viewModelScope)

        locationRepository.getAllLocations()
            .onEach { newLocations ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(locations = newLocations)
                }
            }
            .launchIn(viewModelScope)
    }

    fun updateLocationModal(newState: LocationsModal) {
        _uiState.update {
            it.copy(currentModal = newState)
        }
    }

    fun addLocation(name: String) {
        viewModelScope.launch {
            locationRepository.addNewLocation(
                Location(
                    id = 0,
                    name = name
                )
            )
            _uiState.update {
                it.copy(currentModal = LocationsModal.None)
            }
        }
    }

    fun updateLocation(location: Location) {
        viewModelScope.launch {
            locationRepository.updateLocation(location)
            _uiState.update {
                it.copy(currentModal = LocationsModal.None)
            }
        }
    }

    fun deleteLocation(location: Location) {
        viewModelScope.launch {
            if (locationRepository.getAllLocations().first().size > 1) {
                locationRepository.deleteLocation(location)
            } else {
                _label.send(LocationOTE.ShowSnackbar("There must be at least one location"))
            }
            _uiState.update { currentUiState ->
                currentUiState.copy(currentModal = LocationsModal.None)
            }
        }
    }

    fun changeSelectedLocation(id: Int) {
        viewModelScope.launch {
            preferences.changeLocationId(id)
        }
    }
}

sealed interface LocationOTE {
    data class ShowSnackbar(val text: String) : LocationOTE
}

data class LocationsState(
    val locations: List<Location> = emptyList(),
    val currentModal: LocationsModal = LocationsModal.None,
    val selectedLocationId: Int = -1
)

@Immutable
sealed interface LocationsModal {
    data object Add: LocationsModal
    data class Edit(val location: Location) : LocationsModal
    data class Delete(val location: Location) : LocationsModal
    data object None : LocationsModal
}
