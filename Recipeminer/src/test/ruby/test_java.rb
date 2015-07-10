require 'test/unit'
require 'shoulda/integrations/test_unit'

require 'main/init'

class TestJava < Test::Unit::TestCase
  context "The java integrations" do
    should "be able to provide 'magic' access to de.* and eu.* Java-packages" do
      assert_nothing_raised do 
        assert_instance_of(java.lang.class, de) 
        assert_instance_of(java.lang.class, eu)
      end
    end
  end
end
