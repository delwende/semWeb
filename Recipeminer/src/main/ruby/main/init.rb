module Init
  class << self
    def add_magic_package_access
      [:de, :eu].each do |meth|
        Kernel.module_eval <<-EOM
          def #{meth}
            JavaUtilities.get_package_module_dot_format('#{meth}')    
          end
        EOM
      end
    end
  end
  require 'bundler'
  Bundler.require
  Bundler.require :development
    
  require 'java'
  Init::add_magic_package_access
  Debugger::settings[:autoeval] = 1
  Debugger::settings[:autolist] = 1
  Debugger.start if $0.end_with? 'rdebug'
end