Vagrant.configure('2') do |config|

  config.vm.box = 'bento/centos-7.1'
  config.vm.box_check_update = false

  if Vagrant.has_plugin?('vagrant-omnibus')
    config.omnibus.chef_version = 'latest'
  end

  if Vagrant.has_plugin?('vagrant-cachier')
    config.cache.scope = :box
    config.cache.auto_detect = false
    config.cache.enable :yum
    config.cache.enable :gem
    config.cache.enable :chef_gem
    config.cache.enable :generic, { :cache_dir => '/var/chef/cache' }
  end

  config.vm.define "dev" do |dev|
    dev.vm.hostname = "dev"
    dev.vm.network :private_network, ip: '192.168.33.100'

    dev.vm.provider 'virtualbox' do |vb|
      vb.memory = 1024
    end

    config.vm.provider "libvirt" do |dm, override|
      dm.memory = 1024
    end

    dev.vm.provision :chef_zero, install: true do |chef|
      chef.verbose_logging
      chef.nodes_path = 'cookbooks'
      chef.file_cache_path = '/var/chef/cache'
      chef.add_recipe 'sample-app::default'
      chef.json = {}
    end
  end

end
