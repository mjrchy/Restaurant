import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../api/axios'


function CreateRestaurant() {
    const [name, setName] = useState('')
    const [location, setLocation] = useState('')
    const [rating, setRating] = useState(0)
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(false)
    const navigate = useNavigate()

    async function handleCreateRestaurant(e) {
    e.preventDefault()
    setError('')
    setLoading(true)

    try {

    await api.post(
    "/api/restaurants",
    {
        name,
        rating,
        location
    });

    console.log("Restaurant Creation is successful");
    

      navigate('/restaurant')
    } catch (err) {
      console.error(err)
      setError('Restaurant creation failed. Try again.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div style={{ padding: '2rem', maxWidth: 400, margin: '0 auto' }}>
      <h1>Create Restaurant</h1>
      <form onSubmit={handleCreateRestaurant} style={{ display: 'flex', flexDirection: 'column', gap: '0.75rem' }}>
        <input
          type="text"
          placeholder="Restaurant Name"
          value={name}
          onChange={e => setName(e.target.value)}
          required
        />

        <input
          type="number"
          placeholder="Rating"
          value={rating}
          onChange={e => setRating(e.target.value)}
          required
        />

        <input
          type="text"
          placeholder="Location"
          value={location}
          onChange={e => setLocation(e.target.value)}
          required
        />

        <button type="submit" disabled={loading}>
          {loading ? 'Creating...' : 'Create Restaurant'}
        </button>

        {error && <div style={{ color: 'red' }}>{error}</div>}
      </form>

      <p style={{ marginTop: '1rem' }}>
        See other restaurant?{' '}
        <a href="/restaurant" style={{ textDecoration: 'underline' }}>
          Click here
        </a>
      </p>
    </div>
  )
}

export default CreateRestaurant