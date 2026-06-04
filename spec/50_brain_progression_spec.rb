require 'spec_helper'

def get_question(regexp, last_command_started)
  values = last_command_started.stdout.scan(regexp).last
  return values.last.strip
end

def get_answer(question)
  progression = question.split.map{ |el| el == '..' ? el : el.to_i }
  sub_progression = question.scan(/\-?\d+[\-?\d \.]+\-?\d/).first.split.map{ |el| el == '..' ? el : el.to_i }
  difference = (sub_progression.last - sub_progression.first) / (sub_progression.length - 1)
  index = progression.index(sub_progression.first)
  first = sub_progression.first - difference * index
  hiddenMemberIndex = progression.index('..')
  hiddenMember = first + difference * hiddenMemberIndex
  return hiddenMember.to_s
end

RSpec.describe 'bin/brain-progression', type: :aruba do
  let(:regexp) { /Question:((?: \-?\d+| \.{2}){5,})/ }

  before(:each) do
    file_path = File.expand_path('../code/app/build/install/app/bin/app', __dir__)
    expect(File).to exist(file_path)
    run_command file_path
  end
  before(:each) do
    expect(last_command_started).not_to be_stopped
    type('5')
  end
  before(:each) do
    expect(last_command_started).not_to be_stopped
    type('Tirion')
  end
  before(:each) do
    expect(last_command_started.output).to match_a_correct_progression_question(regexp)
  end

  it 'has description' do
    expect(last_command_started).not_to be_stopped
    expect(last_command_started).to have_output /Hello, Tirion/
    expect(last_command_started).to have_output /What number is missing in the progression?/
  end

  it 'works' do
    question = get_question(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(get_answer(question))

    question = get_question(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(get_answer(question))

    question = get_question(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(get_answer(question))

    expect(last_command_started).to be_successfully_executed
    expect(last_command_started).to have_output /Congratulations, Tirion!/
  end

  it 'fail1' do
    # wrong answer
    question = get_question(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type((get_answer(question).to_i + 1).to_s)

    expect(last_command_started).to be_successfully_executed
    expect(last_command_started).to have_output /Let's try again, Tirion!/

    expect(last_command_started).not_to have_output /Congratulations, Tirion!/
  end

  it 'fail2' do
    question = get_question(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(get_answer(question))

    # wrong answer
    question = get_question(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type((get_answer(question).to_i + 1).to_s)

    expect(last_command_started).to be_successfully_executed
    expect(last_command_started).to have_output /Let's try again, Tirion!/

    expect(last_command_started).not_to have_output /Congratulations, Tirion!/
  end

  it 'fail3' do
    question = get_question(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(get_answer(question))

    question = get_question(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type(get_answer(question))

    # wrong answer
    question = get_question(regexp, last_command_started)
    expect(last_command_started).not_to be_stopped
    type((get_answer(question).to_i + 1).to_s)

    expect(last_command_started).to be_successfully_executed
    expect(last_command_started).to have_output /Let's try again, Tirion!/

    expect(last_command_started).not_to have_output /Congratulations, Tirion!/
  end
end
