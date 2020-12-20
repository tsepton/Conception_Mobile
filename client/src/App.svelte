<script>
  import room from "./stores/room.js";
  import { Router, Route, navigate } from "svelte-routing";
  import Home from "./pages/Home.svelte";
  import Room from "./pages/Room.svelte";
  import Toast from "./components/Toast.svelte";

  // ----------------- Room navigation -------------------
  // If room id was specified within URL, attempt the connection,
  // if not, wait untill a room is specified
  let id = parseInt(location.href.split("/").pop());
  $: !Number.isNaN(id) && room.join(id);
  $: $room && $room.id
    ? navigate($room.id, { replace: true })
    : navigate("/", { replace: true });
</script>

<style>
  :global(body) {
    --back1: #140e45;
    --back2: #25178a;
    --highlight1: #11998e;
    --highlight2: #38ef7d;
    --error: #f71735;
    overflow: hidden;
  }
</style>

<Toast />
<Router>
  <Route path="/">
    <Home />
  </Route>
  <Route>
    <Room />
  </Route>
</Router>
