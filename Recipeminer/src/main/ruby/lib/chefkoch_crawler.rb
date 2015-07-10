# encoding: utf-8

require 'main/init'
require 'lib/config'
require 'net/http'
require 'set'
require 'pp'
require 'yaml'

module Crawling
  module URLBuilder
    
    # Searches in url_template for insertion marks like §param$ and replaces
    # this insertions marks with the values either given by the params hash
    # or (if no corrsponding key is fouund there) with the value of the defaults
    # hash
    def construct_query_url(url_template, params, 
        defaults = {}, param_delimiter = '§')
      params = merge_defaults_into_params(params, defaults)
      url = url_template.dup
      while /§(?<key>.+?)§/ =~ url
        url.gsub!($&) do |match| 
          insertion = params[key.to_sym]
          raise "Substitution for #{key} unknown in #{params}" unless insertion
          insertion
        end
      end
      url
    end
  
    def merge_defaults_into_params(params, defaults)
      sym_defaults = defaults.str_keys_to_sym_keys
      params.merge(sym_defaults) {|k,left,r| left}
    end
  end
    
  module ChefkochAPIURLBuilder
    include URLBuilder 
    
    def construct_sequential_search_url(start)
      conf = @conf[:'search-api']
      url_template = conf[:'base-url'] + conf[:'format']
      search_defaults = conf[:'query-param-defaults']    
      construct_query_url(url_template, {:start => start}, search_defaults)
    end
  
    def construct_recipe_url(showid)
      conf = @conf[:'recipe-api']
      url_template = conf[:'base-url'] + conf[:'format']
      construct_query_url(url_template, {:showid => showid.to_s})
    end  
  end
  
  class ChefkochRecipeData
    attr_reader :uuid, :datahash
    alias_method :data, :datahash
    java_import 'de.recipeminer.persistence.Db4oController'
    
    def initialize(uuid, hash)
      @uuid = uuid
      @datahash = hash
    end
    
    #currently persists into TestObjectContainer
    def persist_in_db4o
      db4oc = Db4oController.get_instance
      test_oc = db4oc.get_test_object_container
      db4oc.releaseTestObjectContainer
      self
    end
        
    def persist_in_file(path=".")
      File.open("#{path}/#{id}", 'w') do |f|
        f.write(JSON.generate(data))
      end
    end
  end
   
  class ChefkochCrawler
    include ChefkochAPIURLBuilder
    attr_reader :ducplicate_requests 
    
    def initialize
      @loaded_recipe_ids = Set.new
      @duplicate_requests = 0
      @conf = Config::Config.instance[:crawling][:chefkoch]
      @uuid_prefix = @conf[:'uuid-prefix']
    end
    
    # TODO: generalize for consecutive requests
    def get_recipes
      search_uri = construct_sequential_search_url(42)
      recipe_list = parse_json_from_uri(search_uri)['result']
      recipe_list.each do |r|
        id = r['RezeptID'].to_i
        if @loaded_recipe_ids.include?(id)
          @duplicate_requests += 1 
        else
          showid = r['RezeptShowID'].to_i
          recipe = fetch_recipe(id, showid)
          recipe.persist_in_db4o
          
          
          @loaded_recipe_ids.add(id)
        end
      end
    end
    
    def fetch_recipe(id, showid)
      recipe_uri = construct_recipe_url(showid)
      json = parse_json_from_uri(recipe_uri)
      uuid = "#@uuid_prefix#{id}"
      recipe_data = json['result'].first
      recipe_data.delete('rezept_bilder')
      ChefkochRecipeData.new(uuid, recipe_data)
    end
    
    def parse_json_from_uri(uri)
      uri = uri.instance_of?(URI) ? uri : URI.parse(uri)
      JSON.parse(Net::HTTP.get(uri))
    end
  end
end