require 'spec_helper'

RSpec.describe 'bin/brain-games', type: :aruba do
  before(:each) do
    file_path = File.expand_path('../code/app/build/install/app/bin/app', __dir__)
    expect(File).to exist(file_path)
    run_command file_path
  end
  before(:each) do
    expect(last_command_started).not_to be_stopped
    type('1')
  end
  before(:each) do
    expect(last_command_started).not_to be_stopped
    type('Tirion')
  end

  it 'works' do
    expect(last_command_started).to be_successfully_executed
    expect(last_command_started).to have_output /Welcome to the Brain Games!/
    expect(last_command_started).to have_output /May I have your name?/
    expect(last_command_started).to have_output /Hello, Tirion!/
  end
end
