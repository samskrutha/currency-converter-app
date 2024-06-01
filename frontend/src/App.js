// src/App.js
import React, { useState } from 'react';
import axios from 'axios';

function App() {
    const [fromCurrency, setFromCurrency] = useState('');
    const [toCurrency, setToCurrency] = useState('');
    const [amount, setAmount] = useState('');
    const [convertedAmount, setConvertedAmount] = useState(null);

    const convertCurrency = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/convert?from=${fromCurrency}&to=${toCurrency}&amount=${amount}`);
            setConvertedAmount(response.data);
        } catch (error) {
            console.error("There was an error fetching the conversion rate!!", error);
        }
    };

    return (
        <div className="App">
            <h1>Currency Converter</h1>
            <input type="text" value={fromCurrency} onChange={e => setFromCurrency(e.target.value)} placeholder="From Currency" />
            <input type="text" value={toCurrency} onChange={e => setToCurrency(e.target.value)} placeholder="To Currency" />
            <input type="number" value={amount} onChange={e => setAmount(e.target.value)} placeholder="Amount" />
            <button onClick={convertCurrency}>Convert</button>
            {convertedAmount && <div>{convertedAmount}</div>}
        </div>
    );
}

export default App;
