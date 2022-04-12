function docReady(fn) {
    if (document.readyState === "complete" || document.readyState === "interactive") {
        setTimeout(fn, 1);
    }
	else {
        document.addEventListener("DOMContentLoaded", fn);
    }
}

docReady(function() {
	var modal = document.createElement("div");
	modal.className = 'the-modal';
	modal.innerHTML = `
		<div class="the-modal-content">
			<span class="the-modal-close">&times;</span>
			<p>Some text in the Modal..</p>
		</div>
	`;

	document.body.appendChild(modal);

	console.log(document.body.lastChild);

	var span = document.getElementsByClassName("the-modal-close")[0];

	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
		modal.style.display = "none";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}
});
