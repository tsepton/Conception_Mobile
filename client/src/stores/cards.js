import socket from "./websockets.js"
import { writable } from 'svelte/store';

const cardsStore = writable(undefined);