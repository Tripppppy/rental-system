module.exports = {

  root: true,
  "parser": "vue-eslint-parser",
  "parserOptions": {
    "parser": "babel-eslint",
    "ecmaVersion": 8,
    "sourceType": "module"
  },
  'extends': [
    'plugin:vue/essential',
    '@vue/standard'
  ],
  rules: {
    // allow async-await
    'generator-star-spacing': 'off',
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'vue/no-parsing-error': [2, { 'x-invalid-end-tag': false }],
    'no-undef': 'off',
    'semi': 'off',
    'space-before-function-paren': 'off',
    'no-tabs': 'off',
    'no-mixed-spaces-and-tabs': 'off',
    'space-infix-ops': 'off',
    'no-trailing-spaces': 'off',
    'comma-dangle': 'off',
    'indent': 'off',
    'padded-blocks': 'off',
    'no-useless-escape': 'off',
  }
}
