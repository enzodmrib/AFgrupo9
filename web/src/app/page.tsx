"use client"

import { api } from '@/libs/axios'
import { useRouter } from 'next/navigation'
import { useEffect, useRef, useState } from 'react'

export default function Home() {
  const router = useRouter()

  const boardingSelectRef = useRef<HTMLSelectElement>(null)
  const destinationSelectRef = useRef<HTMLSelectElement>(null)
  const flightSelectRef = useRef<HTMLSelectElement>(null)
  const ticketSelectRef = useRef<HTMLSelectElement>(null)
  const seatSelectRef = useRef<HTMLSelectElement>(null)
  const hotelSelectRef = useRef<HTMLSelectElement>(null)
  const roomSelectRef = useRef<HTMLSelectElement>(null)
  const eventSelectRef = useRef<HTMLSelectElement>(null)
  const eventTicketSelectRef = useRef<HTMLSelectElement>(null)

  const [packagesData, setPackagesData] = useState([])
  const [placesData, setPlacesData] = useState([])
  const [flightsData, setFlightsData] = useState([])
  const [ticketsData, setTicketsData] = useState([])
  const [seatsData, setSeatsData] = useState([])
  const [hotelsData, setHotelsData] = useState([])
  const [eventsData, setEventsData] = useState([])

  const selectedHotel: any = hotelsData.find((hotel: any) => hotelSelectRef.current?.value === hotel.id)
  const selectedFlight: any = flightsData.find((flight: any) => flight.id === Number(flightSelectRef.current?.value))
  const selectedEvent: any = eventsData.find((event: any) => event.id === Number(eventSelectRef.current?.value))

  useEffect(() => {
    if (typeof window !== 'undefined') {
      if (!localStorage.getItem('user-id')) {
        router.push('/signIn')
      } else {
        api.defaults.headers.common['user-id'] = localStorage.getItem('user-id')

        fetchPackages()
        fetchPlaces()
        fetchHotels()
        fetchEvents()
      }
    }
  }, [])

  async function fetchPackages() {
    const response = await api.get('/packages')

    setPackagesData(response.data)
  }

  async function fetchPlaces() {
    const response = await api.get('/places')

    setPlacesData(response.data.places)
  }

  async function fetchFlights() {
    if (!boardingSelectRef.current || !destinationSelectRef.current) return

    const response = await api.post('/flights', {
      boardingId: boardingSelectRef.current.value,
      destinationId: destinationSelectRef.current.value
    })

    setFlightsData(response.data.flights)
  }

  async function fetchTickets() {
    if (flightSelectRef.current) {
      const response = await api.get(`/tickets/flight/${flightSelectRef.current.value}`)

      if (response.data) {
        setTicketsData(response.data.availableTickets)
      }
    }
  }

  async function fetchSeats() {
    if (flightSelectRef.current) {
      const response = await api.get(`/seats/flight/${flightSelectRef.current.value}`)

      if (response.data) {
        setSeatsData(response.data.seats)
      }
    }
  }

  async function fetchHotels() {
    const response = await api.get('/hotels')

    setHotelsData(response.data.data)
  }

  async function fetchEvents() {
    const response = await api.get('/events')

    setEventsData(response.data)
  }

  async function addPackage() {
    if (
      !boardingSelectRef.current ||
      !destinationSelectRef.current ||
      !flightSelectRef.current ||
      !ticketSelectRef.current ||
      !seatSelectRef.current ||
      !hotelSelectRef.current ||
      !roomSelectRef.current ||
      !eventSelectRef.current ||
      !selectedFlight ||
      !selectedHotel ||
      !selectedEvent
    ) return

    console.log(hotelsData, hotelSelectRef.current.value)


    const response = await api.post('/packages', {
      flightTicketId: ticketSelectRef.current.value,
      flightTrajectory: `${selectedFlight.boarding_location} -> ${selectedFlight.destination_location} (${selectedFlight.type})`,
      hotelRoomId: selectedHotel.id,
      roomType: roomSelectRef.current.value,
      eventTicketId: eventSelectRef.current.value,
      eventName: selectedEvent.name
    })

    if (response.status === 200) {
      fetchPackages()
    }
  }

  async function removePackage(packageId: number) {
    const response = await api.delete(`/packages/${packageId}`)

    if (response.status === 200) {
      fetchPackages()
    }
  }

  return (
    <div className='m-8'>
      <h1 className='text-2xl mb-2 mt-6'>Meus pacotes</h1>
      <div className='flex flex-col gap-2'>
        {packagesData.map((item: any) => (
          <div key={item.id} className='p-2 border-2 border-black rounded-md flex items-center justify-between'>
            <div>
              <p>Numero da passagem do voo: {item.flight_ticket_id}</p>
              <p>Trajeto do voo: {item.flight_description}</p>
              <p>Hotel: {item.hotel_room_id}</p>
              <p>Tipo de quarto: {item.room_type}</p>
              <p>Evento: {item.event_name}</p>
              <p>Numero do ticket do evento: {item.event_ticket_id}</p>
            </div>

            <button onClick={() => removePackage(item.id)} className='flex items-center justify-center bg-red-500 font-bold p-4 rounded-md text-white'>X</button>
          </div>
        ))}
      </div>
      <h1 className='text-2xl mb-2 mt-6'>Adicionar pacote de viagem</h1>
      <div className='grid grid-cols-3 gap-4'>
        <div className='p-4 border-black border-2 grid rounded-md grid-cols-3 gap-2'>
          <p className='col-span-3'>Embarque e destino</p>
          <select
            ref={boardingSelectRef}
            name="boardingSelect"
            className='p-4 border-2 border-zinc-700 rounded-md'
          >
            {placesData.map((place: any) => <option key={place.id} value={place.id}>{place.name}</option>)}
          </select>
          <select
            ref={destinationSelectRef}
            name="destinationSelect"
            className='p-4 border-2 border-zinc-700 rounded-md '
          >
            {placesData.map((place: any) => <option key={place.id} value={place.id}>{place.name}</option>)}
          </select>
          <button onClick={fetchFlights} className='p-4 rounded-md text-white bg-black'>Pesquisar voos</button>
          <select
            ref={flightSelectRef}
            name="flightSelect"
            className='p-4 border-2 border-zinc-700 rounded-md col-span-2'
          >
            {flightsData.map((flight: any) => <option key={flight.id} value={flight.id}>{`${flight.boarding_location} -> ${flight.destination_location} (${flight.type})`}</option>)}
          </select>

          <button onClick={() => {
            fetchTickets()
            fetchSeats()
          }} className='p-4 rounded-md text-white bg-black'>Pesquisar passagens e assentos disponiveis</button>
          <select
            ref={ticketSelectRef}
            name="ticketSelect"
            className='p-4 border-2 border-zinc-700 rounded-md'
          >
            {ticketsData.map((ticket: any) => <option key={ticket.id} value={ticket.id}>{ticket.id}</option>)}
          </select>
          <select
            ref={seatSelectRef}
            name="seatSelect"
            className='p-4 border-2 border-zinc-700 rounded-md'
          >
            {seatsData.map((seat: any) => <option key={seat.id} value={seat.id}>{seat.code}</option>)}
          </select>
        </div>
        <div className='p-4 border-black border-2 grid rounded-md grid-cols-3 gap-2'>
          <p className='col-span-3'>Hotel e tipo de quarto</p>
          <select
            ref={hotelSelectRef}
            name="hotelSelect"
            className='p-4 border-2 border-zinc-700 rounded-md'
          >
            {hotelsData.map((hotel: any) => <option key={hotel.id} value={hotel.id}>{hotel.name} - {hotel.address.city}</option>)}
          </select>
          {selectedHotel && (
            <select
              ref={roomSelectRef}
              name="roomSelect"
              className='p-4 border-2 border-zinc-700 rounded-md'
            >
              {selectedHotel.roomCategories.map((category: any) => <option key={category} value={category}>{category}</option>)}
            </select>
          )}
        </div>
        <div className='p-4 border-black border-2 grid rounded-md grid-cols-3 gap-2'>
          <p className='col-span-3'>Evento</p>
          <select
            ref={eventSelectRef}
            name="eventSelect"
            className='p-4 border-2 border-zinc-700 rounded-md'
          >
            {eventsData.map((event: any) => <option key={event.id} value={event.id}>{event.name}</option>)}
          </select>
        </div>
        <div className='col-span-3 flex items-center justify-center'>
          <button onClick={addPackage} className='p-4 rounded-md text-white bg-black'>Fechar pacote</button>
        </div>
      </div>
    </div>
  )
}
