require 'aruba/rspec'

Aruba.configure do |config|
  config.startup_wait_time = 0.5
  config.exit_timeout = 0.3
  config.io_wait_timeout = 0.1
end

RSpec::Matchers.define :be_stopped do
  # TODO: A delay is needed before each console input,
  # otherwise the tests will fail.
  sleep 0.2
  match do |actual|
    actual.stopped?
  end
  failure_message do |actual|
    "The command should be stopped, but it is running.\n\n#{actual.output}"
  end
  failure_message_when_negated do |actual|
    "The command should be executing, but it is stopped.\n\n#{actual.output}"
  end
end

RSpec::Matchers.define :match_a_correct_even_question do |expected|
  match do |actual|
    actual.match(expected)
  end
  diffable
  failure_message do |actual|
    output_lines = [
      "Line with question is incorrect",
      "Should be: 'Question: <number>'"
    ]
    output_lines.join("\n")
  end
end

RSpec::Matchers.define :match_a_correct_prime_question do |expected|
  match do |actual|
    actual.match(expected)
  end
  diffable
  failure_message do |actual|
    output_lines = [
      "Line with question is incorrect",
      "Should be: 'Question: <number>'"
    ]
    output_lines.join("\n")
  end
end

RSpec::Matchers.define :match_a_correct_gcd_question do |expected|
  match do |actual|
    actual.match(expected)
  end
  diffable
  failure_message do |actual|
    output_lines = [
      "Line with question is incorrect",
      "Should be: 'Question: <number1> <number2>'"
    ]
    output_lines.join("\n")
  end
end

RSpec::Matchers.define :match_a_correct_calc_question do |expected|
  match do |actual|
    actual.match(expected)
  end
  diffable
  failure_message do |actual|
    output_lines = [
      "Line with question is incorrect",
      "Should be: 'Question: <number1> <operation> <number2>'"
    ]
    output_lines.join("\n")
  end
end

RSpec::Matchers.define :match_a_correct_progression_question do |expected|
  match do |actual|
    actual.match(expected)
  end
  diffable
  failure_message do |actual|
    output_lines = [
      "Line with question is incorrect",
      "Should be example: 'Question: 8 10 12 .. 16 18 20 22 24 26'",
      "Progression must contain at least 'five' elements and hidden element marker must be 'two' dots"
    ]
    output_lines.join("\n")
  end
end
