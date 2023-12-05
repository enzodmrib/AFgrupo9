import axios from "axios";

export const flightsApi = axios.create({
    baseURL: 'http://localhost:3001/flightapp',
})

export const hotelsApi = axios.create({
    baseURL: 'http://localhost:4321',
})

export const eventsApi = axios.create({
    baseURL: 'http://localhost:8080',
})