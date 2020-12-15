<script>
  import { Router, Route } from "svelte-routing";
  import Navbar from "./components/Navbar.svelte";
  import Home from "./pages/Home.svelte";
  import Room from "./pages/Room.svelte";

  export let url = "";

  let roomId;
  $: url, roomId = window.location.href.split("/").pop();
</script>

<Router {url}>
  <Navbar />
  <Route path="/">
    <Home />
  </Route>
  <Route>
    <Room id={roomId} />
  </Route>
</Router>
