module.exports = {
  /** @type {import('tailwindcss').Config} */
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      colors: {
        'game-bg': '#10121a',
        'game-gold': '#ffd453',
        'game-gold-dark': '#ffc107',
        'game-red': '#9a1111',
        'game-nav-bg': 'rgba(0,0,0,0.9)',
        'game-text': '#d4c093',
        primary: {
          light: '#4da6ff',
          DEFAULT: '#0078ff',
          dark: '#0057b8',
        },
        secondary: {
          light: '#f8f9fa',
          DEFAULT: '#e9ecef',
          dark: '#dee2e6',
        },
      },
      fontFamily: {
        'utmalexander': ['utmalexander', 'Verdana', 'Geneva', 'Tahoma', 'sans-serif'],
        'roboto': ['Roboto', 'sans-serif'],
        'dosis': ['Dosis', 'sans-serif'],
      },
      animation: {
        'moveY': 'moveYDiv1 3s ease-in-out infinite',
        'fadeleft': 'fadeleft 1.5s ease-in-out',
        'fadeup': 'fadeup 1s ease-in-out',
        'light1': 'light 20s linear infinite',
        'light2': 'light 20s linear 10s infinite',
        'ani2': 'ani2 5s linear infinite',
      },
      keyframes: {
        moveYDiv1: {
          '0%': { transform: 'translateY(0px)' },
          '50%': { transform: 'translateY(-10px)' },
          '100%': { transform: 'translateY(0px)' },
        },
        fadeleft: {
          '0%': { right: '-200px', opacity: '0' },
          '100%': { right: '0px', opacity: '1' },
        },
        fadeup: {
          '0%': { top: '860px', opacity: '0' },
          '100%': { top: '760px', opacity: '1' },
        },
        light: {
          '0%': { left: '0px', bottom: '-50%' },
          '100%': { left: '0px', bottom: '60%', opacity: '0.5' },
        },
        ani2: {
          '0%': { left: '400px' },
          '30%': { left: '420px' },
          '60%': { left: '410px' },
          '100%': { left: '400px' },
        },
      },
    },
  },
  plugins: [],
};
