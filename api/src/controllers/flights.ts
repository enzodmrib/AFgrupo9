import { flightsApi } from "../libs/axios";

export async function getFlightLocations(req, res) {
  const response = await flightsApi.get('/places', {
    headers: {
      'user-id': req.headers["user-id"]
    }
  }).catch(e => e.response)

  return res.status(response.status).json(response.data)
}

export async function getFlights(req, res) {
  const response = await flightsApi.post('/flights/locations', req.body, {
    headers: {
      'user-id': req.headers["user-id"]
    }
  }).catch(e => e.response)

  return res.status(response.status).json(response.data)
}

export async function getFlightTickets(req, res) {
  const { flightId } = req.params

  const response = await flightsApi.get(`/tickets/flight/${flightId}`, {
    headers: {
      'user-id': req.headers["user-id"]
    }
  }).catch(e => e.response)

  return res.status(response.status).json(response.data)
}

export async function getFlightSeats(req, res) {
  const { flightId } = req.params

  const response = await flightsApi.get(`/seats/flight/${flightId}`, {
    headers: {
      'user-id': req.headers["user-id"]
    }
  }).catch(e => e.response)

  return res.status(response.status).json(response.data)
}

export async function bookFlight(req, res) {
  const { ticketId } = req.params

  const response = await flightsApi.put(`/tickets/book/${ticketId}`, req.body, {
    headers: {
      'user-id': req.headers["user-id"]
    }
  }).catch(e => e.response)

  return res.status(response.status).json(response.data)
}

export async function cancelFlight(req, res) {
  const { ticketId } = req.params

  const response = await flightsApi.put(`/tickets/cancel/${ticketId}`, req.body, {
    headers: {
      'user-id': req.headers["user-id"]
    }
  }).catch(e => e.response)

  return res.status(response.status).json(response.data)
}