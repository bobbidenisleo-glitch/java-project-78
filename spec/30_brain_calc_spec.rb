require 'spec_helper'

def get_expression_data(regexp, last_command_started)
  values = last_command_started.stdout.scan(regexp).last
  values.map{ |value| value.match?(/\-?\d+/) ? value.to_i : value }
end

RSpec.describe 'bin/brain-calc', type: :aruba do
  let(:regexp) { /Question: (\-?\d+) ([\+\-\*]) (\-?\d+)/ }

  before(:each) do
    file_path = File.expand_path('../code/app/build/install/app/bin/app', __dir__)
    expect(File).to exist(file_path)
    run_command file_path
  end
  before(:each) do
    expect(last_command_started).not_to be_stopped
    type('3')
  end
  before(:each) do
    expect(last_command_started).not_to be_stopped
    type('Tirion')
  end
  before(:each) do
    expect(last_command_started.output).to match_a_correct_calc_question(regexp)
  end

  it 'has description' do
    expect(last_command_started).not_to be_stopped
    expect(last_command_started).to have_output /Hello, Tirion/
    expect(last_command_started).to have_output /What is the result of the expression?/
  end

  it 'works' do
    number1, operator, number2 = get_expression_data(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(number1.send(operator, number2).to_s)

    number1, operator, number2 = get_expression_data(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(number1.send(operator, number2).to_s)

    number1, operator, number2 = get_expression_data(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(number1.send(operator, number2).to_s)

    expect(last_command_started).to be_successfully_executed
    expect(last_command_started).to have_output /Congratulations, Tirion!/
  end

  it 'fail1' do
    # wrong answer
    number1, operator, number2 = get_expression_data(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type((number1.send(operator, number2) + 1).to_s)

    expect(last_command_started).to be_successfully_executed
    expect(last_command_started).to have_output /Let's try again, Tirion!/

    expect(last_command_started).not_to have_output /Congratulations, Tirion!/
  end

  it 'fail2' do
    number1, operator, number2 = get_expression_data(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(number1.send(operator, number2).to_s)

    # wrong answer
    number1, operator, number2 = get_expression_data(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type((number1.send(operator, number2) + 1).to_s)

    expect(last_command_started).to be_successfully_executed
    expect(last_command_started).to have_output /Let's try again, Tirion!/

    expect(last_command_started).not_to have_output /Congratulations, Tirion!/
  end

  it 'fail3' do
    number1, operator, number2 = get_expression_data(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(number1.send(operator, number2).to_s)

    number1, operator, number2 = get_expression_data(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(number1.send(operator, number2).to_s)

    # wrong answer
    number1, operator, number2 = get_expression_data(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type((number1.send(operator, number2) + 1).to_s)

    expect(last_command_started).to be_successfully_executed
    expect(last_command_started).to have_output /Let's try again, Tirion!/

    expect(last_command_started).not_to have_output /Congratulations, Tirion!/
  end
end
