require 'test/unit'
require 'shoulda/integrations/test_unit'

require 'main/init'
require 'lib/config'

class TestConfig < Test::Unit::TestCase
  context "#{Config::Config}" do
    context  "in the test enviroment" do
      setup do
        @config = Config::Config.instance
      end
      should "be able to load values of category inner " +
        "in the file conf_test.yaml for the first time" do
        retrieved = @config[:'conf test'][:inner][:value1]
        assert_equal 'value1', retrieved
        retrieved = @config[:'conf test'][:inner][:'2']
        assert_equal 'two', retrieved
        retrieved = @config[:'conf test'][:inner][:list]
        assert_equal ['a', 'heterogenous', "list", 42], retrieved
      end
      should "be able to load a hash from the crawling settings" do
        search_api = @config[:crawling][:chefkoch][:'search-api']
        retrieved = search_api[:'query-param-defaults']
        assert_equal({'limit' => 30}, retrieved)
      end
      should "be able to retrieve values of #{:'conf test'} again" +
        "from its cache" do
        retrieved = @config[:'conf test'][:inner][:value1]
        assert_equal 'value1', retrieved
        retrieved = @config[:'conf test'][:inner][:'2']
        assert_equal 'two', retrieved
      end
      should "provide pathnames for resources" do
        path = 'recipeminer.db4o'
        abs_path = File.join(Config.resource_path, path)
        expected = Pathname.new(abs_path)
        puts "Found: #{abs_path}"
        assert_equal(expected, Config.get_resource(path))
      end
      should "provide pathnames for test resources" do
        path = 'recipeminer_test.db4o'
        abs_path = File.join(Config.resource_path, path)
        expected = Pathname.new(abs_path)
        assert_equal(expected, Config.get_test_resource(path))
      end

#      this expectation would require recursive search through the loaded yaml
#      to find all Hash instances - postponed for the moment
#      should "be to retrieve a hash as leave of config tree that uses instances "+
#        "of Symbol as keys" do
#        search_config = @config[:crawling][:chefkoch][:'search-api']
#        retrieved_hash = search_config[:'query-param-defaults']
#        assert_equal({:limit => 30}, retrieved_hash)
#      end
    end
    should "add 'str_keys_to_sym_keys instance method to #{Hash}"do
      mixed_hash = {'strkey' => :trixie, :symkey => :samson, 'strkey2' => :chip}
      converted_hash = mixed_hash.str_keys_to_sym_keys
      sym_hash = {:strkey => :trixie, :symkey => :samson, :strkey2 => :chip}
      assert_equal sym_hash, converted_hash
    end
  end
end