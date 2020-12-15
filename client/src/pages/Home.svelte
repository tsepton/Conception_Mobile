<script>
  import Button from "../components/Button.svelte";
</script>

<style>
  main {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  h1,
  h6 {
    color: white;
    font-weight: 100;
  }
  h6 {
    padding-bottom: 1rem;
  }
  div[center] {
    text-align: center;
  }
</style>

<main>
  <div center>
    <h1>Welcome to Mõla</h1>
    <h6>What do you want to do ?</h6>
    <Button>Join room</Button>
    <Button>Create room</Button>
  </div>
</main>
