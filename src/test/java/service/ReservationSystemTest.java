package service;// Importamos las clases y métodos necesarios para las pruebas
import dao.ConferenceRoomDao;
import model.ConferenceRoom;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

// Definimos la clase de pruebas
public class ReservationSystemTest {

    // Declaración de variables necesarias para las pruebas
    private ConferenceRoomDao roomDao;
    private ReservationSystem reservationSystem;

    // Método de configuración antes de cada prueba
    @Before
    public void setUp() {
        // Creamos un mock del ConferenceRoomDao
        roomDao = mock(ConferenceRoomDao.class);

        // Creamos una instancia de ReservationSystem con el mock
        reservationSystem = new ReservationSystem(roomDao);
    }

    // Prueba para el método reserveRoom
    @Test
    public void testReserveRoom() {
        // Seleccionamos un roomId para la prueba
        String roomId = "101";

        // Creamos una instancia de ConferenceRoom con el roomId y una capacidad de 10
        ConferenceRoom room = new ConferenceRoom(roomId, 10);

        // Configuramos el comportamiento del mock (roomDao)
        when(roomDao.getRoomById(roomId)).thenReturn(room);
        when(roomDao.updateRoom(any(ConferenceRoom.class))).thenReturn(true);

        // Llamamos al método que queremos probar
        assertTrue(reservationSystem.reserveRoom(roomId));

        // Verificamos que se llamaron los métodos esperados en el mock (roomDao)
        verify(roomDao, times(1)).getRoomById(roomId);
        verify(roomDao, times(1)).updateRoom(room);
    }

    // Prueba para el método cancelReservation
    @Test
    public void testCancelReservation() {
        // Seleccionamos un roomId para la prueba
        String roomId = "102";

        // Creamos una instancia de ConferenceRoom con el roomId y una capacidad de 15
        ConferenceRoom room = new ConferenceRoom(roomId, 15);
        room.setReserved(true);

        // Configuramos el comportamiento del mock (roomDao)
        when(roomDao.getRoomById(roomId)).thenReturn(room);
        when(roomDao.updateRoom(any(ConferenceRoom.class))).thenReturn(true);

        // Llamamos al método que queremos probar
        assertTrue(reservationSystem.cancelReservation(roomId));

        // Verificamos que se llamaron los métodos esperados en el mock (roomDao)
        verify(roomDao, times(1)).getRoomById(roomId);
        verify(roomDao, times(1)).updateRoom(room);
    }

    // Prueba para el método getAvailableRooms
    @Test
    public void testGetAvailableRooms() {
        // Creamos una lista de salas de conferencias
        List<ConferenceRoom> rooms = Arrays.asList(
                new ConferenceRoom("201", 20),
                new ConferenceRoom("202", 25),
                new ConferenceRoom("203", 30)
        );

        // Configuramos el comportamiento del mock (roomDao)
        when(roomDao.getAllRooms()).thenReturn(rooms);

        // Llamamos al método que queremos probar
        List<ConferenceRoom> availableRooms = reservationSystem.getAvailableRooms();

        // Verificamos que el resultado es el esperado
        assertEquals(3, availableRooms.size());
        assertFalse(availableRooms.get(0).isReserved());
        assertFalse(availableRooms.get(1).isReserved());
        assertFalse(availableRooms.get(2).isReserved());

        // Verificamos que se llamó el método getAllRooms en el mock (roomDao)
        verify(roomDao, times(1)).getAllRooms();
    }
}

//Comentarios detallados:
//
//Importaciones:
//
//Importamos las clases y métodos necesarios para las pruebas, incluyendo clases de JUnit y Mockito.
//Declaración de Variables:
//
//Declaramos las variables roomDao y reservationSystem necesarias para las pruebas.
//Método de Configuración (setUp):
//
//Utilizamos el método setUp con la anotación @Before para inicializar el mock (roomDao) y la instancia de ReservationSystem antes de cada prueba.
//Prueba testReserveRoom:
//
//Creamos una sala de conferencias simulada y configuramos el comportamiento del mock para el método getRoomById y updateRoom.
//Llamamos al método reserveRoom y verificamos que los métodos del mock fueron llamados correctamente.
//Prueba testCancelReservation:
//
//Creamos una sala de conferencias simulada con reservación y configuramos el comportamiento del mock.
//Llamamos al método cancelReservation y verificamos que los métodos del mock fueron llamados correctamente.
//Prueba testGetAvailableRooms:
//
//Creamos una lista de salas de conferencias simuladas y configuramos el comportamiento del mock para getAllRooms.
//Llamamos al método getAvailableRooms y verificamos que el resultado es el esperado, además de verificar que el método del mock fue llamado correctamente.
//Estas pruebas cubren diferentes aspectos de la clase ReservationSystem para asegurar que sus métodos se comporten según lo esperado.