import { writable } from 'svelte/store';

const host = location.origin.replace('http', 'ws');
export default writable(new WebSocket(`${host}/ws/`));
