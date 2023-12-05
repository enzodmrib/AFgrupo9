import express from 'express'
import { login, signUp } from './controllers/auth'
import { bookFlight, cancelFlight, getFlightLocations, getFlightSeats, getFlightTickets, getFlights } from './controllers/flights'
import { checkinHotel, checkoutHotel, getHotelById, getHotels } from './controllers/hotels'
import { bookEvent, getAttendees, getEventTickets, getEvents } from './controllers/events'
import { addPackage, deletePackage, getAllPackages, getPackage } from './controllers/package'
import { checkUserAuth } from './middlewares/auth'

const router = express.Router()

router.post('/login', login)
router.post('/signup', signUp)
router.get('/places', getFlightLocations)
router.post('/flights', getFlights)
router.get('/tickets/flight/:flightId', getFlightTickets)
router.get('/seats/flight/:flightId', getFlightSeats)
router.put('/tickets/book/:ticketId', bookFlight)
router.put('/tickets/cancel/:ticketId', cancelFlight)

router.get('/hotels', getHotels)
router.get('/hotels/:hotelId', getHotelById)
router.post('/hotels/checkin', checkinHotel)
router.post('/hotels/checkout', checkoutHotel)

router.get('/events', getEvents)
router.get('/events/:eventId/tickets', getEventTickets)
router.get('/attendees', getAttendees)
router.post('/events/:eventId/tickets', bookEvent)

router.get('/packages', checkUserAuth, getAllPackages)
router.get('/packages/:packageId', checkUserAuth, getPackage)
router.post('/packages', checkUserAuth, addPackage)
router.delete('/packages/:packageId', checkUserAuth, deletePackage)

export default router