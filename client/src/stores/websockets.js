import { writable } from 'svelte/store';

const store = writable(new WebSocket('ws://localhost:9000/ws'));

store.subscribe(socket => socket.onerror = () => {
  console.warn("Connection lost, retrying...");
  store.set(new WebSocket('ws://localhost:9000/ws'));
});

export default {
  subscribe: store.subscribe,
}