require 'spec_helper'

describe 'sample-app::default' do
  let(:chef_run) { ChefSpec::SoloRunner.new(platform: 'centos', version: '7.0').converge(described_recipe) }

  xit 'includes needed recipes' do
    expect(chef_run).to include_recipe('sample-app::java')
  end
end
