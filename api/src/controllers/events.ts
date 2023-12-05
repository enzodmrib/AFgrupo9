import { eventsApi } from "../libs/axios";

export async function getEvents(req, res) {
  const response = await eventsApi.get('/events').catch(e => e.response)

  return res.status(response.status).json(response.data)
}

export async function getEventTickets(req, res) {
  const response = await eventsApi.get(`/events/${req.params.eventId}`).catch(e => e.response)

  return res.status(response.status).json(response.data)
}

export async function getAttendees(req, res) {
  const response = await eventsApi.get('/attendees').catch(e => e.response)

  return res.status(response.status).json(response.data)
}

export async function bookEvent(req, res) {
  const { eventId } = req.params  

  const response = await eventsApi.post(`/events/${eventId}/tickets`, req.body).catch(e => e.response)

  return res.status(response.status).json(response.data)
}