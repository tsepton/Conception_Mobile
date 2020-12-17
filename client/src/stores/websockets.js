import { writable } from 'svelte/store';

const roomsStore = writable('');
const cardsStore = writable('');

const socket = new WebSocket('ws://localhost:9000/ws');

socket.onmessage = function (e) {
    const message = JSON.parse(e.data);
    switch (message.type) {
        case "rooms":
            setRooms(message.data);
    }
}

function setRooms(rooms) {
    console.log(rooms);
    roomsStore.set(rooms);
}

export default {
    subscribe: roomsStore.subscribe,
}
