require 'spec_helper'

def get_number(regexp, last_command_started)
  values = last_command_started.stdout.scan(regexp).last
  values.last.to_i
end

RSpec.describe 'bin/brain-even', type: :aruba do
  let(:regexp) { /Question: (\-?\d+)/ }

  before(:each) do
    file_path = File.expand_path('../code/app/build/install/app/bin/app', __dir__)
    expect(File).to exist(file_path)
    run_command file_path
  end
  before(:each) do
    expect(last_command_started).not_to be_stopped
    type('2')
  end
  before(:each) do
    expect(last_command_started).not_to be_stopped
    type('Tirion')
  end
  before(:each) do
    expect(last_command_started.output).to match_a_correct_even_question(regexp)
  end

  it 'has description' do
    expect(last_command_started).not_to be_stopped
    expect(last_command_started).to have_output /Hello, Tirion/
    expect(last_command_started).to have_output /Answer 'yes' if the number is even, otherwise answer 'no'./
  end

  it 'works' do
    number1 = get_number(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(number1.even? ? 'yes' : 'no')

    number2 = get_number(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(number2.even? ? 'yes' : 'no')

    number3 = get_number(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(number3.even? ? 'yes' : 'no')

    expect(last_command_started).to be_successfully_executed
    expect(last_command_started).to have_output /Congratulations, Tirion!/
  end

  it 'fail1' do
    # wrong answer
    number1 = get_number(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(number1.even? ? 'no' : 'yes')

    expect(last_command_started).to be_successfully_executed
    expect(last_command_started).to have_output /Let's try again, Tirion!/

    expect(last_command_started).not_to have_output /Congratulations, Tirion!/
  end

  it 'fail2' do
    number1 = get_number(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(number1.even? ? 'yes' : 'no')

    # wrong answer
    number2 = get_number(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(number2.even? ? 'no' : 'yes')

    expect(last_command_started).to be_successfully_executed
    expect(last_command_started).to have_output /Let's try again, Tirion!/

    expect(last_command_started).not_to have_output /Congratulations, Tirion!/
  end

  it 'fail3' do
    number1 = get_number(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(number1.even? ? 'yes' : 'no')

    number2 = get_number(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(number2.even? ? 'yes' : 'no')

    # wrong answer
    number3 = get_number(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(number3.even? ? 'no' : 'yes')

    expect(last_command_started).to be_successfully_executed
    expect(last_command_started).to have_output /Let's try again, Tirion!/

    expect(last_command_started).not_to have_output /Congratulations, Tirion!/
  end
end
