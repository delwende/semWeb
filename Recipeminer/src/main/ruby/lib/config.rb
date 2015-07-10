require 'singleton'
java_import('de.recipeminer.tools.Config') { |cls| 'JConfig'}


module Config
  # defines methods of Config module
  class << self
    def project_home_path
      @project_home ||= begin
        filepath = File.expand_path(__FILE__, '.')
        k = 0
        #Ascend from $PROJECT_ROOT/lib/config.rb to $PROJECT_ROOT
        Pathname(filepath).ascend {|dir| break dir if k==2; k+=1}.to_s
      end
    end
    alias_method  :project_path, :project_home_path
  
    def resource_path(env=get_env)
      @ressource_path ||= begin
        Pathname.new(File.join(::Config::project_path, 'resources', env))
      end
    end
    
    def config_path(env=get_env)
      @config_path ||= begin
        Pathname.new(File.join(::Config::project_path, 'config', env))
      end      
    end
  
    def get_env
      @env ||= ENV['MINERENV'] || 'development'
    end
    
    def get_resource(path)
      abs_path = File.join(::Config::resource_path, path)
      Pathname.new(abs_path)
    end
    
    #convenience method to access test resources when program is run in a
    # differtent MINERENV
    def get_test_resource(path)
      abs_path = File.join(::Config::resource_path('test'), path)
      Pathname.new(abs_path)
    end
  end
    
  class Config
    include Singleton
    def initialize
      @config_cache = Hash.new
    end
    
    def get(category)
      conf =  @config_cache[category.to_sym]
      conf ||= get_yaml_config(category)
      if conf.nil? || (! conf.respond_to? :'[]')
        raise "Unable to retrieve config hash for category: #{category} " +
          "(#{category.class}"
      end
      conf
    end
    
    alias_method :'[]', :get
    
    private
    def get_yaml_config(category)
      begin
        filename = category.to_s.gsub(/\P{ASCII}|\p{Space}/, '_')
        path = File.join('config', "#{filename}.yaml")
        path = JConfig.get_instance.get_resource(path).get_absolute_path
        puts "Looking up config in #{path}"
        yaml_content = YAML.load_file(path)
        @config_cache[category.to_sym] = yaml_content[category.to_sym]
      rescue Exception => e
        msg = "An error occurred getting configuration file for #{category}:\n"+
          "#{e}"
        exc = Exception.new(msg)
        exc.set_backtrace(e.backtrace)
        raise exc
      end
    end
    # for debugging purpose
    attr_reader :'config_cache' 
  end
end
class Hash
  def str_keys_to_sym_keys
    result = self.dup
    str_keys = result.keys.select {|k| k.instance_of? String}
    str_keys.each { |k| result[k.to_sym] = result.delete(k) }
    result
  end
end

