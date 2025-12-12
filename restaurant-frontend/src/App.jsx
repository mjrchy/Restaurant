import { useState } from 'react'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Login from './pages/Login.jsx'
import Register from './pages/Register.jsx'
import Restaurant from './pages/Restaurant.jsx'
import CreateRestaurant from './pages/CreateRestaurant.jsx'
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/restaurant" element={<Restaurant />} />
        <Route path="/create-restaurant" element={<CreateRestaurant />} />
      </Routes>
    </BrowserRouter>

  )
}

export default App
