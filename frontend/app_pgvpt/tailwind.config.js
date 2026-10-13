/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#1E3A8A', // Bleu Gorée profond
          light: '#2563EB',
          dark: '#1E3A8A',
        },
        secondary: {
          DEFAULT: '#D97706', // Ocre / Latérite
          light: '#F59E0B',
          dark: '#B45309',
        },
        accent: {
          DEFAULT: '#059669', // Vert Casamance
        },
        background: {
          DEFAULT: '#FAFAF9', // Sable clair
        },
        forest: {
          DEFAULT: '#1c231d',
          deep: '#101612',
        },
        paper: {
          DEFAULT: '#f3f1e9',
        },
        ivory: {
          DEFAULT: '#ebe8de',
        },
        clay: {
          DEFAULT: '#8b8070',
        },
        gold: {
          DEFAULT: '#cfa661',
        },
        ink: {
          DEFAULT: '#151515',
        }
      },
      fontFamily: {
        sans: ['Inter', 'sans-serif'], // Police moderne pour le corps
        serif: ['Playfair Display', 'serif'], // Police élégante pour les titres
      },
      boxShadow: {
        'glow-primary': '0 10px 25px -5px rgba(30, 58, 138, 0.4)',
        'glow-secondary': '0 10px 25px -5px rgba(217, 119, 6, 0.4)',
      }
    },
  },
  plugins: [],
}
