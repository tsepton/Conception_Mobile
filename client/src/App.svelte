<script>
  let roomId;

  $: roomId = window.location.href.split("/").pop();
</script>

<main>{roomId}</main>
