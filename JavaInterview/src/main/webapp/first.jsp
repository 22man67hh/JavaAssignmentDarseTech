<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Assignment</title>
    <script>
       
        async function fetchBooks() {
            const response = await fetch('/JavaInterview/books');
            const html = await response.text();
            const table = document.getElementById('booklist');
            table.innerHTML += html;

            const deleteButtons = document.querySelectorAll('.delete-btn');
            deleteButtons.forEach(button => {
                button.addEventListener('click', async function () {
                    const row = button.closest('tr');
                    const isbn = row.cells[1].innerText;
                    await deleteBook(isbn);
                    row.remove();
                });
            });

            const updateButtons = document.querySelectorAll('.update-btn');
            updateButtons.forEach(button => {
                button.addEventListener('click', async function () {
                    const row = button.closest('tr');
                    const name = row.cells[0].innerText;
                    const isbn = row.cells[1].innerText;
                    openUpdateForm(name, isbn);
                });
            });
        }

        async function deleteBook(isbn) {
            await fetch(`/JavaInterview/books/${isbn}`, { method: 'DELETE' });
        }

        async function addBook() {
            const name = document.getElementById("add-name").value;
            const isbn = document.getElementById("add-isbn").value;

            await fetch('/JavaInterview/books', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams({ name, isbn })
            });
            fetchBooks();
           
        }

        async function updateBook() {
            const name = document.getElementById("update-name").value;
            const isbn = document.getElementById("update-isbn").value;

            await fetch(`/JavaInterview/books/${isbn}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams({ name })
            });

            fetchBooks();
            closeUpdateForm();
        }

        function openUpdateForm(name, isbn) {
            document.getElementById("update-name").value = name;
            document.getElementById("update-isbn").value = isbn;
            document.getElementById("update-section").style.display = "block";
        }

        function closeUpdateForm() {
            document.getElementById("update-section").style.display = "none";
            document.getElementById("update-form").reset(); 
        }

        window.onload = fetchBooks;
    </script>
</head>
<body>
  
    <section id="add-section">
        <h2>Add Book</h2>
        <form id="add-form" onsubmit="event.preventDefault(); addBook();">
            <input type="text" id="add-name" placeholder="Enter Book Name" required><br><br>
            <input type="text" id="add-isbn" placeholder="Enter ISBN Number" required><br><br>
            <button type="submit">Add Book</button>
        </form>
    </section>

    <hr>

   
    <section id="update-section" style="display: none;">
        <h2>Update Book</h2>
        <form id="update-form" onsubmit="event.preventDefault(); updateBook();">
            <input type="hidden" id="update-isbn">
            <input type="text" id="update-name" placeholder="Enter New Book Name" required><br><br>
            <button type="submit">Update Book</button>
            <button type="button" onclick="closeUpdateForm();">Cancel</button>
        </form>
    </section>

    <hr>

  
    <section id="list-section">
        <h2>List of Books</h2>
        <table id="booklist" border="1">
            <tr>
                <th>Name</th>
                <th>ISBN</th>
                <th>Actions</th>
            </tr>
        </table>
    </section>
</body>
</html>
