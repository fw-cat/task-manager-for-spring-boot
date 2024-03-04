/**
 * 
 */
const deleteModal = document.getElementById("deleteConfirm")
const deleteBtns = document.querySelectorAll(".btn-delete-modal")

window.onload = () => {
	deleteBtns.forEach((deleteBtn) => {
		deleteBtn.addEventListener("click", (e) => {
			console.log(e.target, e.target.dataset)
			const datasets = e.target.dataset
			const modal = new bootstrap.Modal(deleteModal)
			deleteModal.querySelector("#deleteUser").innerHTML = datasets.user
			deleteModal.querySelector("#deleteForm").action = `/delete/complate/${datasets.id}`
			modal.show()
		})
	})
}
 