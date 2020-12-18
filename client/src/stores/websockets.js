let socket = new WebSocket('ws://localhost:9000/ws');

socket.onerror = () => {
    console.warn("Connection lost, retrying...");
    socket = new WebSocket('ws://localhost:9000/ws');
}

export default socket;