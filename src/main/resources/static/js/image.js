console.log('URL');
function readURL(files) {
	if (files && files[0]) {
		const image = document.getElementById('image');
		if (image) {
			const url = URL.createObjectURL(files[0]);
			image.src = url;
		}
	}
}

const imagePicker = document.getElementById('file');

if (imagePicker) {
	imagePicker.addEventListener('change', function (event) {
		readURL(event.currentTarget.files);
	});
}
