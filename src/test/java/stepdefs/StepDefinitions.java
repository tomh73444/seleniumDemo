package stepdefs;

import java.util.ArrayList;
import java.util.List;


import org.junit.Assert;

import data.Book;
import data.BookStore;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {
	private BookStore store;
	private List<Book> foundBooks;

	@Before
	public void setUp() {
		store = new BookStore();
		foundBooks = new ArrayList<>();
	}

	@Given("I have the following books in the store")
	public void i_have_the_following_books_in_the_store(DataTable dataTable) {
		
		List<Book> books=	new ArrayList<Book>();		  		 	  
		  
			List<List<String>> list = dataTable.asLists(String.class);
			for(int i=1; i<list.size(); i++) { //i starts from 1 because i=0 represents the header
				String title=list.get(i).get(0); 
				String author=list.get(i).get(1);
				books.add(new Book(title, author));
			}
			 store.addAllBooks(books);

	}

	@When("I search for books by author {string}")
	public void i_search_for_books_by_author_erik_larson(String author) {
		
		 foundBooks=store.booksByAuthor(author);
	}

	@Then("I find {int} books")
	public void i_find_books(Integer count) {
		
		Assert.assertTrue(count.equals(foundBooks.size()));
	}
}
