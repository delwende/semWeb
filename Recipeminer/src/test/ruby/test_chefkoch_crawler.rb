# To change this template, choose Tools | Templates
# and open the template in the editor.

$:.unshift File.join(File.dirname(__FILE__),'..','lib')

require 'test/unit'
require 'shoulda/integrations/test_unit'

require 'main/init'
require 'lib/chefkoch_crawler'


class TestChefkochCrawler < Test::Unit::TestCase
  context "#{Crawling::ChefkochCrawler}" do
    setup do
      @crawler = Crawling::ChefkochCrawler.new
    end
    context "using the #{Crawling::ChefkochAPIURLBuilder} module" do
      should "be able to constuct a URL to retrieve a recipe" do
        expected_url = 'http://api.chefkoch.de/api/1.0/api-recipe.php' +
          '?ID=506631145837800'
        actual_url = @crawler.construct_recipe_url(506631145837800)
        assert_equal(expected_url, actual_url)
      end
    
      should "be able to construct a URL to sequetially query all available" +
        "recipes" do
        expected_url = 'http://api.chefkoch.de/api/1.0/api-recipe-search.php' +
          '?Suchbegriff=&start=30&limit=30'
        actual_url = @crawler.construct_sequential_search_url(30)
        assert_equal(expected_url, actual_url)
      end
    
#      should "be able to convert a hash with default values into a param hash"+
#        ", converting String keys into Symbol keys" do
#        params = {:start => 15}
#        defaults = {"limit" => 30}
#        result = @crawler.merge_defaults_into_params(params, defaults)
#        assert_equal({:start => 15, :limit => 30}, result)
#      end
    end
    should "be able to retrieve 30 recipes from the online api" do
      @crawler.get_recipes
    end
  
  
  end

  

end

